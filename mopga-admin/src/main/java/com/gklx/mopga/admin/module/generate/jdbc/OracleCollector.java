package com.gklx.mopga.admin.module.generate.jdbc;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.GenTableColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.jdbc.pojo.SqlItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service("oracle")
public class OracleCollector extends JdbcManager implements IBaseCollector {

    private final String databaseType = "1521";

    @Override
    public IPage<TableEntity> selectDbTableList(IPage<TableEntity> page, DatabaseEntity database, TableQueryForm params) {
        List<TableEntity> tableList = new ArrayList<>();
        SqlItem sqlItem = JdbcSpiLoader.SqlDefines.get(databaseType).getTableSqlList().get(0);
        String sql = sqlItem.getSql();
        List<String> columns = sqlItem.getColumns();
        String tableNames = params.getTableNames();

        String whereSql = "";
        if (StrUtil.isNotEmpty(tableNames)) {
            String[] tableNameArr = Convert.toStrArray(tableNames);
            whereSql = whereSql + String.format(" AND t.TABLE_NAME in (%s)", getSqlStringForIn(Arrays.asList(tableNameArr))); //
        }
        if (StrUtil.isNotEmpty(params.getKeyword())) {
            whereSql = whereSql + String.format(" AND t.TABLE_NAME like %s", "'%" + params.getKeyword() + "%'"); //

        }
        sql = String.format(sql, whereSql);


        Map<String, Object> countMap = this.querySingleRow(database, getCountSql(sql, "*"), null);
        Integer total = MapUtil.getInt(countMap, "COUNT(*)");

        sql = getPaginationSql(sql, page.getCurrent(), page.getSize());
        List<Map<String, Object>> maps = this.queryMultipleRows(database, sql, null);
        if (maps != null && !maps.isEmpty()) {
            for (Map<String, Object> map : maps) {
                TableEntity genTable = new TableEntity();
                genTable.setTableName(MapUtil.getStr(map, columns.get(0)));
                genTable.setTableComment(MapUtil.getStr(map, columns.get(1)));
                tableList.add(genTable);
            }
        }
        page.setTotal(total);
        page.setRecords(tableList);
        return page;
    }

    @Override
    public List<GenTableColumnEntity> selectDbTableColumnsByName(DatabaseEntity database, String tableName) {
        SqlItem sqlItem = JdbcSpiLoader.SqlDefines.get(databaseType).getTableFieldSqlList().get(0);
        List<String> columns = sqlItem.getColumns();
        List<Map<String, Object>> maps = this.queryMultipleRows(database, sqlItem.getSql(), Arrays.asList(tableName, tableName));
        List<GenTableColumnEntity> tableColumnList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(maps)) {
            for (Map<String, Object> map : maps) {
                GenTableColumnEntity genTableColumn = new GenTableColumnEntity();
                genTableColumn.setColumnName(MapUtil.getStr(map, columns.get(0)));
                genTableColumn.setIsNull(MapUtil.getBool(map, columns.get(1)));
                genTableColumn.setIsPk(MapUtil.getBool(map, columns.get(2)));
                genTableColumn.setSort(MapUtil.getInt(map, columns.get(3)));
                genTableColumn.setColumnComment(MapUtil.getStr(map, columns.get(4)));
                genTableColumn.setIsIncrement(MapUtil.getBool(map, columns.get(5)));
                genTableColumn.setColumnType(MapUtil.getStr(map, columns.get(6)));
                genTableColumn.setColumnDefault(MapUtil.getStr(map, columns.get(7)));
                tableColumnList.add(genTableColumn);
            }
        }
        return tableColumnList;
    }

    @Override
    public String getPaginationSql(String originalSql, Long pageNum, Long pageSize) {
        long page = (pageNum == null || pageNum < 1) ? 1 : pageNum;
        long size = (pageSize == null || pageSize < 1) ? 10 : pageSize;

        // 计算分页参数
        long startRow = (page - 1) * size;
        long endRow = page * size;

        StringBuilder sqlBuilder = new StringBuilder(originalSql.length() + 200);

        // 外层查询：处理起始行过滤
        if (startRow > 0) {
            sqlBuilder.append("SELECT * FROM ( ");
        }

        // 中层查询：处理结束行过滤
        sqlBuilder.append(" SELECT TMP_PAGE.*, ROWNUM PAGEHELPER_ROW_ID FROM ( ");

        // 原始查询SQL（确保包含ORDER BY）
        sqlBuilder.append(originalSql.trim());
        sqlBuilder.append(" ");

        // 闭合中层查询并添加结束行条件
        sqlBuilder.append(" ) TMP_PAGE WHERE ROWNUM <= ").append(endRow);

        // 闭合外层查询并添加起始行条件
        if (startRow > 0) {
            sqlBuilder.append(" ) WHERE PAGEHELPER_ROW_ID > ").append(startRow);
        }

        return sqlBuilder.toString();
    }
}
