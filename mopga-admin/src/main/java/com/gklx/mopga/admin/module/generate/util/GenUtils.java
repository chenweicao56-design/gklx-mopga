package com.gklx.mopga.admin.module.generate.util;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.module.generate.domain.entity.*;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 代码生成器 工具类
 *
 * @author ruoyi
 */
@Slf4j
public class GenUtils {

    public static final VelocityEngine velocityEngine;

    static {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        velocityEngine.init();
    }

    public static VelocityContext initContext() {
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("t", new VelocityTools());
        return velocityContext;
    }


    public static String generateCode(DatabaseEntity database,
                                      TableVo genTable,
                                      TemplateEntity templateEntity,
                                      TemplateCodeItemEntity templateCodeItemEntity,
                                      List<TemplateCodeItemEntity> templateCodeItemEntities) {
        VelocityContext context = GenUtils.prepareContext(database, genTable, templateEntity, templateCodeItemEntity, templateCodeItemEntities);
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, templateCodeItemEntity.getFileName(), new StringReader(templateCodeItemEntity.getContent()));
        return writer.toString();
    }

    public static VelocityContext prepareContext(DatabaseEntity database, TableVo genTable,
                                                 TemplateEntity templateEntity,
                                                 TemplateCodeItemEntity templateCodeItemEntity,
                                                 List<TemplateCodeItemEntity> templateCodeItemEntities) {
        VelocityContext velocityContext = initContext();
        velocityContext.put("backendProjectPath", database.getBackendProjectPath());
        velocityContext.put("frontProjectPath", database.getFrontProjectPath());
        Map<String, Object> tableMap = buildTable(database, genTable, templateCodeItemEntity);
        tableMap.forEach(velocityContext::put);
        velocityContext.put("subTables", genTable.getSubTables());
        //模板信息
        buildFilePackages(database, velocityContext, templateEntity, templateCodeItemEntities);
        return velocityContext;
    }

    private static Map<String, Object> buildTable(DatabaseEntity database, TableVo genTable, TemplateCodeItemEntity templateCodeItemEntity) {
        Map<String, Object> tableMap = new HashMap<>();
        tableMap.put("tableName", genTable.getTableName());
        tableMap.put("tableComment", genTable.getTableComment());
        tableMap.put("backendAuthor", genTable.getBackendAuthor());
        tableMap.put("backendDate",
                ObjUtil.isNotNull(genTable.getBackendDate()) ?
                        DateUtil.format(genTable.getBackendDate(), "yyyy-MM-dd HH:mm:ss") :
                        DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss")
        );
        tableMap.put("copyright", genTable.getCopyright());
        tableMap.put("frontAuthor", genTable.getFrontAuthor());
        tableMap.put("frontDate", ObjUtil.isNotNull(genTable.getFrontDate()) ?
                DateUtil.format(genTable.getFrontDate(), "yyyy-MM-dd HH:mm:ss") :
                DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss")
        );
        tableMap.put("packageName", genTable.getPackageName());
        tableMap.put("moduleName", genTable.getModuleName());
        tableMap.put("isPhysicallyDeleted", genTable.getIsPhysicallyDeleted());
        tableMap.put("wordName", genTable.getWordName());
        tableMap.put("WordName", StrUtil.upperFirst(genTable.getWordName()));
        tableMap.put("word_name", StrUtil.toUnderlineCase(genTable.getWordName()));
        tableMap.put("wordname", StrUtil.toUnderlineCase(genTable.getWordName()).replace("_", "-"));
        tableMap.put("isPage", genTable.getIsPage());
        tableMap.put("isDetail", genTable.getIsDetail());
        tableMap.put("isAdd", genTable.getIsAdd());
        tableMap.put("isUpdate", genTable.getIsUpdate());
        tableMap.put("isDelete", genTable.getIsDelete());
        tableMap.put("isBatchDelete", genTable.getIsBatchDelete());
        tableMap.put("editComponent", genTable.getEditComponent());
        tableMap.put("formCountLine", genTable.getFormCountLine());
        tableMap.put("schemaName", database.getSchemaName());
        tableMap.put("permission", genTable.getPermission());
        tableMap.put("log", genTable.getLog());
        tableMap.put("isTree", genTable.getIsTree());
        tableMap.put("isImport", genTable.getIsImport());
        tableMap.put("isExport", genTable.getIsExport());

        String extendedData = genTable.getExtendedData();
        if (StrUtil.isNotBlank(extendedData)) {
            tableMap.putAll(JSONUtil.parseObj(extendedData));
        }
        String backendProjectPath = database.getBackendProjectPath();
        String frontProjectPath = database.getFrontProjectPath();
        String projectPath = StrUtil.isNotBlank(backendProjectPath) ? backendProjectPath : frontProjectPath;
        String filePath = templateCodeItemEntity.getFilePath();
        if (projectPath.contains("/")) {
            tableMap.put("modulePackage",
                    StrUtil.subAfter(filePath, "}/", true)
                            .replace("/", ".")
                            .replaceAll("\\.*$", ""));
        } else {
            tableMap.put("modulePackage",
                    StrUtil.subAfter(filePath, "}\\", true)
                            .replace("\\", ".")
                            .replaceAll("\\.*$", ""));

        }
        //字段信息
        List<GenTableColumnVo> columns = genTable.getColumns();
        if (CollectionUtil.isNotEmpty(columns)) {
            List<GenTableColumnVo> queryColumns = new ArrayList<>();
            List<GenTableColumnVo> uniqueColumns = new ArrayList<>();
            for (GenTableColumnVo column : columns) {
                column.setWordName(StrUtil.upperFirst(column.getFieldName()));
                if (column.getIsPk()) {
                    tableMap.put("primaryKeyColumnName", column.getColumnName());
                    tableMap.put("primaryKeyFieldType", column.getFieldType());
                    tableMap.put("primaryKeyJsType", column.getJsType());
                    tableMap.put("primaryKeyFieldName", column.getFieldName());
                    tableMap.put("primaryKeyIsIncrement", column.getIsIncrement());
                    tableMap.put("primaryKeyUpperFieldName", StrUtil.upperFirst(column.getFieldName()));
                }
                if (column.getIsWhere()) {
                    queryColumns.add(column);
                }
                if (column.getIsUnique()) {
                    uniqueColumns.add(column);
                }
            }
            tableMap.put("queryColumns", queryColumns);
            tableMap.put("isUnique", !uniqueColumns.isEmpty());
            tableMap.put("uniqueColumns", uniqueColumns);

        }
        tableMap.put("columns", genTable.getColumns());
        return tableMap;
    }


    private static void buildFilePackages(DatabaseEntity database, VelocityContext velocityContext, TemplateEntity templateEntity, List<TemplateCodeItemEntity> templateCodeItemEntities) {
        templateCodeItemEntities.forEach(item -> {
            String backendProjectPath = database.getBackendProjectPath();
            String frontProjectPath = database.getFrontProjectPath();
            String projectPath = StrUtil.isNotBlank(backendProjectPath) ? backendProjectPath : frontProjectPath;
            String fileType = item.getFileType();
            String fileName = item.getFileName();
            String filePath = item.getFilePath();
            String modulePackage = "";
            if (fileType.equalsIgnoreCase("java")) {
                if (projectPath.contains("/")) {
                    modulePackage = StrUtil.subAfter(filePath, "}/", true)
                            .replace("/", ".")
                            .replaceAll("\\.*$", "");
                } else {
                    modulePackage = StrUtil.subAfter(filePath, "}\\", true)
                            .replace("\\", ".")
                            .replaceAll("\\.*$", "");

                }
                velocityContext.put(fileName + "ModulePackage", modulePackage);
            }
        });
    }

    public static JSONObject buildFile(DatabaseEntity database, TableVo table, TemplateEntity templateEntity, TemplateCodeItemEntity templateCodeItemEntity, List<TemplateCodeItemEntity> templateCodeItemEntities) {
        JSONObject fileType = new JSONObject();
        String filePath = templateCodeItemEntity.getFilePath();
        String packageName = table.getPackageName();
        String backendProjectPath = database.getBackendProjectPath();
        String frontProjectPath = database.getFrontProjectPath();
        if (backendProjectPath.contains("/") || frontProjectPath.contains("/")) {
            packageName = packageName.replace(".", "/");
        } else {
            packageName = packageName.replace(".", "\\");
        }
        String wordName = table.getWordName();
        filePath = StrUtil.replace(filePath, "${backendProjectPath}", backendProjectPath);
        filePath = StrUtil.replace(filePath, "${frontProjectPath}", frontProjectPath);
        filePath = StrUtil.replace(filePath, "${package}", packageName);
        filePath = StrUtil.replace(filePath, "${module}", table.getModuleName());
        filePath = StrUtil.replace(filePath, "${wordName}", StrUtil.lowerFirst(wordName));
        filePath = StrUtil.replace(filePath, "${WordName}", StrUtil.upperFirst(wordName));
        filePath = StrUtil.replace(filePath, "${word_name}", StrUtil.toSymbolCase(wordName, '_'));
        filePath = StrUtil.replace(filePath, "${wordname}", StrUtil.toSymbolCase(wordName, '-'));

        String fileName = templateCodeItemEntity.getFileName();
        fileName = StrUtil.replace(fileName, "${package}", packageName);
        fileName = StrUtil.replace(fileName, "${module}", table.getModuleName());
        fileName = StrUtil.replace(fileName, "${wordName}", StrUtil.lowerFirst(wordName));
        fileName = StrUtil.replace(fileName, "${WordName}", StrUtil.upperFirst(wordName));
        fileName = StrUtil.replace(fileName, "${word_name}", StrUtil.toSymbolCase(wordName, '_'));
        fileName = StrUtil.replace(fileName, "${wordname}", StrUtil.toSymbolCase(wordName, '-'));
        fileType.set("fileName", fileName);
        fileType.set("fileType", templateCodeItemEntity.getFileType());
        fileType.set("filePath", filePath + fileName);
        fileType.set("id", templateCodeItemEntity.getId());
        fileType.set("fileContent", GenUtils.generateCode(database, table, templateEntity, templateCodeItemEntity, templateCodeItemEntities));
        fileType.set("checked", true);
        return fileType;
    }

    public static void buildIsBase(GenTableColumnEntity column, Map<String, TemplateColumnEntity> templateBaseClassItemMap) {
        TemplateColumnEntity templateBaseClassItemEntity = templateBaseClassItemMap.get(column.getColumnName().toUpperCase());
        column.setIsBase(ObjUtil.isNotNull(templateBaseClassItemEntity));
    }

    public static void buildFileType(GenTableColumnEntity column, Map<String, TemplateMappingItemEntity> templateMappingItemEntityMap, Map<String, MappingDataEntity> defaultMappingMap) {

        String columnType = column.getColumnType();
        TemplateMappingItemEntity templateMappingItemEntity = templateMappingItemEntityMap.get(columnType.toUpperCase());
        if (ObjUtil.isNotNull(templateMappingItemEntity)) {
            column.setFieldType(templateMappingItemEntity.getBackColumnType());
            column.setJsType(templateMappingItemEntity.getFrontColumnType());
            column.setFrontComponent(templateMappingItemEntity.getFrontComponent());
            return;
        }

        if (columnType.contains("(") && columnType.contains(")")) {
            columnType = columnType.substring(0, columnType.indexOf("("));
            templateMappingItemEntity = templateMappingItemEntityMap.get(columnType.toUpperCase());
            if (ObjUtil.isNotNull(templateMappingItemEntity)) {
                column.setFieldType(templateMappingItemEntity.getBackColumnType());
                column.setJsType(templateMappingItemEntity.getFrontColumnType());
                column.setFrontComponent(templateMappingItemEntity.getFrontComponent());
                return;
            }

        }
        MappingDataEntity mappingDataEntity = defaultMappingMap.get(columnType.toUpperCase());
        if (ObjUtil.isNotNull(mappingDataEntity)) {
            column.setFieldType(mappingDataEntity.getJavaFieldType());
            column.setJsType(mappingDataEntity.getFrontFieldType());
            column.setFrontComponent(mappingDataEntity.getFrontComponent());
            return;
        }
        if (columnType.contains("(") && columnType.contains(")")) {
            columnType = columnType.substring(0, columnType.indexOf("("));
            mappingDataEntity = defaultMappingMap.get(columnType.toUpperCase());
            if (ObjUtil.isNotNull(mappingDataEntity)) {
                column.setFieldType(mappingDataEntity.getJavaFieldType());
                column.setJsType(mappingDataEntity.getFrontFieldType());
                column.setFrontComponent(mappingDataEntity.getFrontComponent());
                return;
            }
        }

    }
}
