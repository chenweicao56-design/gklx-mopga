package com.gklx.mopga.admin.module.generate.jdbc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.jdbc.cache.CacheIdentifier;
import com.gklx.mopga.admin.module.generate.jdbc.cache.CommonCache;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

@Slf4j
public class JdbcManager {

    /**
     * 测试数据库连接是否可用
     */
    public boolean isConnect(DatabaseEntity database) {
        Connection connection = null;
        try {
            DriverManager.setLoginTimeout(database.getTimeout() / 1000); // 转换为秒
            connection = DriverManager.getConnection(database.getUrl(),
                    database.getUserName(),
                    database.getPassword());
            return true;
        } catch (SQLException e) {
            log.error("数据库连接失败，URL: {}，原因：{}", database.getUrl(), e.getMessage());
            return false;
        } finally {
            closeResource(connection, null, null);
        }
    }

    /**
     * 获取数据库连接（优先从缓存获取）
     */
    public Connection getConnection(String username, String password, String url, Integer timeout) throws Exception {
        // 构建缓存标识（使用url而非ip作为标识一部分，更准确）
        CacheIdentifier identifier = CacheIdentifier.builder()
                .ip(extractIpFromUrl(url))  // 从URL提取IP作为标识，更合理
                .username(username)
                .password(password)
                .build();

        // 尝试从缓存获取
        Optional<Object> cacheOption = CommonCache.getInstance().getCache(identifier, true);
        if (cacheOption.isPresent()) {
            JdbcConnect jdbcConnect = (JdbcConnect) cacheOption.get();
            try {
                Connection connection = jdbcConnect.getDruidDataSource().getConnection();
                if (connection != null && !connection.isClosed()) {
                    return connection;
                }
            } catch (Exception e) {
                log.warn("缓存连接不可用，重建连接: {}", e.getMessage());
                jdbcConnect.close();
                CommonCache.getInstance().removeCache(identifier);
            }
        }

        // 缓存未命中或连接不可用，创建新连接
        return createNewConnection(username, password, url, timeout, identifier);
    }

    /**
     * 创建新的数据库连接并缓存
     */
    private Connection createNewConnection(String username, String password, String url,
                                           Integer timeout, CacheIdentifier identifier) throws Exception {
        Properties props = new Properties();
        props.put("url", url);
        props.put("username", username);
        props.put("password", password);
        props.put("connectTimeout", timeout.toString());
        props.put("socketTimeout", timeout.toString());
        // 配置Druid连接池参数，优化性能
        props.put("initialSize", "5");
        props.put("maxActive", "20");
        props.put("minIdle", "5");
        props.put("maxWait", timeout.toString());
//        props.put("testOnBorrow", "true");
//        props.put("validationQuery", "SELECT 1");

        DruidDataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(props);
        dataSource.setBreakAfterAcquireFailure(true);

        Connection connection = dataSource.getConnection();
        CommonCache.getInstance().addCache(identifier, new JdbcConnect(dataSource));
        return connection;
    }

    /**
     * 获取Statement对象
     */
    public Statement getStatement(Connection connection, Integer timeout) throws SQLException {
        Statement statement = connection.createStatement();
        setStatementProperties(statement, timeout);
        return statement;
    }

    /**
     * 获取PreparedStatement对象
     */
    public PreparedStatement getPreparedStatement(Connection connection, Integer timeout, String sql) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        setStatementProperties(statement, timeout);
        return statement;
    }

    /**
     * 设置Statement公共属性
     */
    private void setStatementProperties(Statement statement, Integer timeout) throws SQLException {
        int timeoutSeconds = Math.max(timeout / 1000, 1); // 确保至少1秒超时
        statement.setQueryTimeout(timeoutSeconds);
        statement.setMaxRows(1000); // 限制最大行数，防止内存溢出
    }

    /**
     * 查询单行数据
     */
    public Map<String, Object> querySingleRow(DatabaseEntity database, String sql, List<Object> params) {
        List<Map<String, Object>> result = queryMultipleRows(database, sql, params, 1);
        return CollectionUtil.isNotEmpty(result) ? result.get(0) : new HashMap<>(0);
    }

    /**
     * 查询多行数据
     */
    public List<Map<String, Object>> queryMultipleRows(DatabaseEntity database, String sql, List<Object> params) {
        return queryMultipleRows(database, sql, params, 1000);
    }

    /**
     * 带最大行数限制的查询
     */
    private List<Map<String, Object>> queryMultipleRows(DatabaseEntity database, String sql,
                                                        List<Object> params, int maxRows) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            connection = getConnection(database.getUserName(), database.getPassword(),
                    database.getUrl(), database.getTimeout());
            statement = getPreparedStatement(connection, database.getTimeout(), sql);
            statement.setMaxRows(maxRows);
            setParameters(statement, params);

            log.debug("执行查询SQL: {}", sql);
            resultSet = statement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(metaData.getColumnName(i).toUpperCase(), resultSet.getObject(i));
                }
                resultList.add(rowData);
            }
            log.debug("查询完成，返回行数: {}", resultList.size());
        } catch (Exception e) {
            log.error("执行SQL异常，SQL: {}，参数: {}", sql, params, e);
            throw new RuntimeException("查询数据失败: " + e.getMessage(), e);
        } finally {
            closeResource(connection, statement, resultSet);
        }
        return resultList;
    }

    /**
     * 执行批量SQL
     */
    public boolean executeBatch(DatabaseEntity database, List<String> sqls) {
        if (CollectionUtils.isEmpty(sqls)) {
            log.warn("批量执行的SQL列表为空");
            return true;
        }

        Connection connection = null;
        Statement statement = null;
        boolean autoCommit = true;

        try {
            connection = getConnection(database.getUserName(), database.getPassword(),
                    database.getUrl(), database.getTimeout());
            autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false); // 开启事务

            statement = getStatement(connection, database.getTimeout());

            for (String sql : sqls) {
                if (StrUtil.isNotBlank(sql)) {
                    statement.addBatch(sql);
                    log.debug("添加批量执行SQL: {}", sql);
                }
            }

            statement.executeBatch();
            connection.commit();
            log.debug("批量SQL执行成功，共{}条", sqls.size());
            return true;
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    log.warn("批量执行失败，已回滚事务");
                } catch (SQLException ex) {
                    log.error("事务回滚失败", ex);
                }
            }
            log.error("批量执行SQL异常", e);
            throw new RuntimeException("批量执行失败: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(autoCommit); // 恢复自动提交状态
                } catch (SQLException e) {
                    log.warn("恢复自动提交状态失败", e);
                }
            }
            closeResource(connection, statement, null);
        }
    }

    /**
     * 执行更新操作
     */
    public boolean executeUpdate(DatabaseEntity database, String sql) {
        return executeUpdate(database, sql, Collections.emptyList());
    }

    /**
     * 执行带参数的更新操作
     */
    public boolean executeUpdate(DatabaseEntity database, String sql, List<Object> params) {
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            connection = getConnection(database.getUserName(), database.getPassword(),
                    database.getUrl(), database.getTimeout());
            statement = getPreparedStatement(connection, database.getTimeout(), sql);
            setParameters(statement, params);

            log.debug("执行更新SQL: {}", sql);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            log.error("执行更新SQL异常，SQL: {}，参数: {}", sql, params, e);
            throw new RuntimeException("更新数据失败: " + e.getMessage(), e);
        } finally {
            closeResource(connection, statement, null);
        }
    }

    /**
     * 为PreparedStatement设置参数
     */
    private void setParameters(PreparedStatement statement, List<Object> params) throws SQLException {
        if (CollectionUtil.isNotEmpty(params)) {
            for (int i = 0; i < params.size(); i++) {
                // 使用setObject更通用，支持更多数据类型
                statement.setObject(i + 1, params.get(i));
            }
        }
    }

    /**
     * 生成统计总数的SQL
     */
    public String getCountSql(String sql, String column) {
        StrUtil.isEmpty(column); // 确保column不为空
        return String.format("SELECT COUNT(%s) FROM ( %s ) tmp_count",
                StrUtil.isEmpty(column) ? "*" : column, sql);
    }

    /**
     * 生成分页SQL（适用于MySQL）
     */
    public String getPaginationSql(String originalSql, Long pageNum, Long pageSize) {
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1) {
            log.warn("无效的分页参数，返回原始SQL");
            return originalSql;
        }
        long offset = (pageNum - 1) * pageSize;
        return originalSql + String.format(" LIMIT %d, %d", offset, pageSize);
    }

    /**
     * 生成IN语句的参数字符串
     */
    public String getSqlStringForIn(List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            sb.append("'").append(escapeSql(values.get(i))).append("'");
            if (i < values.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 关闭数据库资源
     */
    private void closeResource(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            log.warn("关闭ResultSet失败", e);
        }

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            log.warn("关闭Statement失败", e);
        }

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            log.warn("关闭Connection失败", e);
        }
    }

    /**
     * 从URL中提取IP地址（简化处理）
     */
    private String extractIpFromUrl(String url) {
        if (StrUtil.isEmpty(url)) {
            return "";
        }
        // 简单处理，实际可能需要更复杂的解析
        String ipPart = url.replaceAll("jdbc:mysql://", "")
                .replaceAll("jdbc:postgresql://", "")
                .split("/")[0];
        return ipPart.split(":")[0];
    }

    /**
     * SQL转义，防止注入
     */
    private String escapeSql(String value) {
        if (StrUtil.isEmpty(value)) {
            return "";
        }
        return value.replace("'", "''");
    }
}
