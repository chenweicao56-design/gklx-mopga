package com.gklx.mopga.admin.ai.mcp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;
import com.gklx.mopga.admin.module.generate.jdbc.IBaseCollector;
import com.gklx.mopga.admin.module.generate.jdbc.JdbcManager;
import com.gklx.mopga.admin.module.generate.manager.DatabaseManager;
import com.gklx.mopga.admin.module.generate.service.GenerateService;
import com.gklx.mopga.admin.module.generate.service.TableService;
import com.gklx.mopga.admin.module.system.login.domain.RequestEmployee;
import com.gklx.mopga.admin.module.system.login.manager.LoginManager;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MCP 工具类：数据库操作和代码生成
 */
@Slf4j
@Service
public class GenerateMcp {

    @Resource
    private DatabaseManager databaseManager;
    @Resource
    private TableService tableService;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private GenerateService generateService;

    @Resource
    LoginManager loginManager;

    @McpTool(description = "通过SQL创建表或更新表")
    public String executeTableSql(
            @McpToolParam(description = "数据库主键") Long databaseId,
            @McpToolParam(description = "表名") String tableName,
            @McpToolParam(description = "SQL类型：CREATE（建表）或 UPDATE（更新）") String sqlType,
            @McpToolParam(description = "SQL语句，多个用;隔开") String sql) {
        log.info("开始执行表SQL操作: databaseId={}, tableName={}, sqlType={}", databaseId, tableName, sqlType);

        if (!"CREATE".equalsIgnoreCase(sqlType) && !"UPDATE".equalsIgnoreCase(sqlType)) {
            log.error("不支持的 SQL 类型: {}", sqlType);
            return String.format("错误：不支持的 SQL 类型 '%s'，仅支持 CREATE（建表）和 UPDATE（更新）", sqlType);
        }

        DatabaseEntity database = databaseManager.getById(databaseId);
        IBaseCollector collector = applicationContext.getBean(database.getDatabaseType(), IBaseCollector.class);

        if (collector instanceof JdbcManager manager) {
            List<String> sqlList = Arrays.stream(sql.trim().split(";"))
                    .filter(s -> !s.trim().isEmpty())
                    .collect(Collectors.toList());

            for (String sqlStatement : sqlList) {
                validateSqlStatement(sqlStatement, database.getDatabaseType());
            }

            manager.executeBatch(database, sqlList);
            log.info("SQL执行成功");
        }

        return String.format("表 %s %s 成功", tableName, sqlType);
    }

    private void validateSqlStatement(String sql, String databaseType) {
        String normalizedSql = sql.trim().toUpperCase();
        boolean isValid = false;
        isValid = normalizedSql.startsWith("CREATE TABLE") ||
                normalizedSql.startsWith("ALTER TABLE");
        if (!isValid) {
            String dbType = databaseType.toUpperCase();
            if ("ORACLE".equals(dbType) ||
                    "POSTGRES".equals(dbType) ||
                    "DM".equals(dbType) ||
                    "KINGBASE".equals(dbType)) {
                isValid = normalizedSql.startsWith("COMMENT ON");
            }

        }
        if (!isValid) {
            log.error("检测到不允许的 SQL 语句: {}", sql);
            throw new IllegalArgumentException(String.format(
                    "不允许的 SQL 操作，仅支持 CREATE TABLE 和 ALTER TABLE 语句。当前语句: %s", sql));
        }
    }

    /**
     * 同步表结构到数据库并生成代码
     */
    @McpTool(description = "同步表结构并生成代码到本地")
    public String syncTableAndGenerateCode(
            @McpToolParam(description = "数据库主键") Long databaseId,
            @McpToolParam(description = "表名") String tableName) {
        log.info("开始同步表结构并生成代码: databaseId={}, tableName={}", databaseId, tableName);
        RequestEmployee requestEmployee = loginManager.getRequestEmployee(1L);
        SmartRequestUtil.setRequestUser(requestEmployee);
        // 同步表结构
        generateService.syncTable(databaseId, true, tableName);
        log.info("表结构同步成功");

        // 生成代码
        return generateCodeByTableId(databaseId, tableName);
    }

    /**
     * 根据表名生成代码并同步到本地
     */
    private String generateCodeByTableId(Long databaseId, String tableName) {
        TableVo table = tableService.getByName(databaseId, tableName);
        if (table == null) {
            log.error("未找到表: databaseId={}, tableName={}", databaseId, tableName);
            return "错误：未找到表";
        }

        List<JSONObject> preview = generateService.preview(table.getTableId());
        if (CollectionUtil.isEmpty(preview)) {
            log.error("生成预览失败: tableId={}", table.getTableId());
            return "错误：生成预览失败";
        }

        JSONArray files = new JSONArray();
        for (JSONObject obj : preview) {
            JSONArray jsonArray = obj.getJSONArray("files");
            if (jsonArray != null) {
                files.addAll(jsonArray);
            }
        }

        if (CollectionUtil.isEmpty(files)) {
            log.warn("未生成任何文件: tableId={}", table.getTableId());
            return "警告：未生成任何文件";
        }

        // 将代码文件写入本地
        int successCount = 0;
        int failCount = 0;
        for (int i = 0; i < files.size(); i++) {
            JSONObject item = files.getJSONObject(i);
            String filePath = item.getStr("filePath");
            String fileContent = item.getStr("fileContent");

            try {
                File file = new File(filePath);
                if (!file.getParentFile().exists()) {
                    FileUtil.mkdir(file.getParentFile());
                }
                FileUtil.writeString(fileContent, filePath, StandardCharsets.UTF_8);
                successCount++;
                log.info("文件生成成功: {}", filePath);
            } catch (Exception e) {
                failCount++;
                log.error("文件生成失败: {}", filePath, e);
            }
        }

        log.info("代码同步完成: 成功{}个, 失败{}个", successCount, failCount);

        if (failCount > 0) {
            return String.format("部分文件生成失败: 成功%d个, 失败%d个", successCount, failCount);
        }
        return String.format("代码同步成功，共生成%d个文件", successCount);
    }
}
