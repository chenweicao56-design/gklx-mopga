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
import com.gklx.mopga.admin.module.generate.manager.TableManager;
import com.gklx.mopga.admin.module.generate.service.GenerateService;
import com.gklx.mopga.admin.module.generate.service.TableService;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @McpTool(description = "通过建表语句生成基础代码")
    public String generateCodeByCreateTableSql(@McpToolParam(description = "数据库主键") Long databaseId,
                                               @McpToolParam(description = "表名") String tableName,
                                               @McpToolParam(description = "建表sql，多个用;隔开") String createTableSql

    ) {
        DatabaseEntity database = databaseManager.getById(databaseId);
        IBaseCollector collector = applicationContext.getBean(database.getDatabaseType(), IBaseCollector.class);

        if (collector instanceof JdbcManager manager) {
            manager.executeBatch(database, Arrays.stream(createTableSql.trim().split(";")).toList());
        }
        generateService.syncTable(databaseId, true, tableName);
        TableVo table = tableService.getByName(databaseId, tableName);

        List<JSONObject> preview = generateService.preview(table.getTableId());

        JSONArray files = new JSONArray();
        if(CollectionUtil.isNotEmpty(preview)){
            for (JSONObject obj : preview) {
                JSONArray jsonArray = obj.getJSONArray("files");
                files.addAll(jsonArray);
            }
        }

        for (int i = 0; i < files.size(); i++) {
            JSONObject item = files.getJSONObject(i);
            String filePath = item.getStr("filePath");
            String fileContent = item.getStr("fileContent");

            File file = new File(filePath);
            if(!file.getParentFile().exists()){
                FileUtil.mkdir(file.getParentFile());
            }
            FileUtil.writeString(fileContent, filePath, StandardCharsets.UTF_8);
        }





        return "同步成功";
    }


}
