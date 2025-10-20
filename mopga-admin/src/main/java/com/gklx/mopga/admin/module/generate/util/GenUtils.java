package com.gklx.mopga.admin.module.generate.util;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gklx.mopga.admin.module.generate.domain.entity.*;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


    public static String generateCode(TableVO genTable,
                                      TemplateEntity templateEntity,
                                      TemplateCodeItemEntity templateCodeItemEntity,
                                      List<TemplateCodeItemEntity> templateCodeItemEntities) {
        VelocityContext context = GenUtils.prepareContext(genTable, templateEntity, templateCodeItemEntity, templateCodeItemEntities);
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, templateCodeItemEntity.getFileName(), new StringReader(templateCodeItemEntity.getContent()));
        return writer.toString();
    }

    public static VelocityContext prepareContext(TableVO genTable,
                                                 TemplateEntity templateEntity,
                                                 TemplateCodeItemEntity templateCodeItemEntity,
                                                 List<TemplateCodeItemEntity> templateCodeItemEntities) {
        VelocityContext velocityContext = new VelocityContext();
        genTable.setLowerCamelCase(StrUtil.lowerFirst(genTable.getWordName()));
        genTable.setUpperCamelCase(StrUtil.upperFirst(genTable.getWordName()));
        genTable.setSnakeCase(StrUtil.toUnderlineCase(genTable.getWordName()));
        genTable.setKabadCase(genTable.getSnakeCase().replace("_", "-"));

        // 表信息
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("tableComment", genTable.getTableComment());
        velocityContext.put("backendAuthor", genTable.getBackendAuthor());
        velocityContext.put("backendDate",
                ObjUtil.isNotNull(genTable.getBackendDate()) ?
                        DateUtil.format(genTable.getBackendDate(), "yyyy-MM-dd HH:mm:ss") :
                        DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss")
        );
        velocityContext.put("copyright", genTable.getCopyright());
        velocityContext.put("frontAuthor", genTable.getFrontAuthor());
        velocityContext.put("frontDate", ObjUtil.isNotNull(genTable.getFrontDate()) ?
                DateUtil.format(genTable.getFrontDate(), "yyyy-MM-dd HH:mm:ss") :
                DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss")
        );
        velocityContext.put("packageName", genTable.getPackageName());
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("isPhysicallyDeleted", genTable.getIsPhysicallyDeleted());
        velocityContext.put("wordName", genTable.getWordName());
        velocityContext.put("lowerCamelCase", genTable.getLowerCamelCase());
        velocityContext.put("upperCamelCase", genTable.getUpperCamelCase());
        velocityContext.put("kabadCase", genTable.getKabadCase());
        velocityContext.put("snakeCase", genTable.getSnakeCase());

        velocityContext.put("isPage", genTable.getIsPage());
        velocityContext.put("isDetail", genTable.getIsDetail());
        velocityContext.put("isAdd", genTable.getIsAdd());
        velocityContext.put("isUpdate", genTable.getIsUpdate());
        velocityContext.put("isDelete", genTable.getIsDelete());
        velocityContext.put("isBatchDelete", genTable.getIsBatchDelete());
        velocityContext.put("editComponent", genTable.getEditComponent());
        velocityContext.put("formCountLine", genTable.getFormCountLine());

        String extendedData = genTable.getExtendedData();
        if (StrUtil.isNotBlank(extendedData)) {
            JSONUtil.parseObj(extendedData).forEach(velocityContext::put);
        }

        String filePath = templateCodeItemEntity.getFilePath();
        if (templateEntity.getProjectPath().contains("/")) {
            velocityContext.put("modulePackage",
                    StrUtil.subAfter(filePath, "}/", true)
                            .replace("/", ".")
                            .replaceAll("\\.*$", ""));
        } else {
            velocityContext.put("modulePackage",
                    StrUtil.subAfter(filePath, "}\\", true)
                            .replace("\\", ".")
                            .replaceAll("\\.*$", ""));

        }
        //字段信息
        List<GenTableColumnVo> columns = genTable.getColumns();
        if (CollectionUtil.isNotEmpty(columns)) {
            List<GenTableColumnVo> queryColumns = new ArrayList<>();
            for (GenTableColumnVo column : columns) {
                column.setUpperCamelCase(StrUtil.upperFirst(column.getFieldName()));
                if (column.getIsPk()) {
                    velocityContext.put("primaryKeyColumnName", column.getColumnName());
                    velocityContext.put("primaryKeyFieldType", column.getFieldType());
                    velocityContext.put("primaryKeyJsType", column.getJsType());
                    velocityContext.put("primaryKeyFieldName", column.getFieldName());
                }
                if (column.getIsWhere()) {
                    queryColumns.add(column);
                }

            }
            velocityContext.put("queryColumns", queryColumns);
        }
        velocityContext.put("columns", genTable.getColumns());
        //模板信息
        buildFilePackages(velocityContext, templateEntity, templateCodeItemEntities);
        return velocityContext;
    }


    private static void buildFilePackages(VelocityContext velocityContext, TemplateEntity templateEntity, List<TemplateCodeItemEntity> templateCodeItemEntities) {
        templateCodeItemEntities.forEach(item -> {
            String fileType = item.getFileType();
            String fileName = item.getFileName();
            String filePath = item.getFilePath();
            String modulePackage = "";
            if (fileType.equalsIgnoreCase("java")) {
                if (templateEntity.getProjectPath().contains("/")) {
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

    public static JSONObject buildFile(TableVO table, TemplateEntity templateEntity, TemplateCodeItemEntity templateCodeItemEntity, List<TemplateCodeItemEntity> templateCodeItemEntities) {
        JSONObject fileType = new JSONObject();


        String projectPath = templateEntity.getProjectPath();
        String filePath = templateCodeItemEntity.getFilePath();
        String packageName = table.getPackageName();
        if (projectPath.contains("/")) {
            packageName = packageName.replace(".", "/");
        } else {
            packageName = packageName.replace(".", "\\");
        }
        String wordName = table.getWordName();
        filePath = StrUtil.replace(filePath, "${package}", packageName);
        filePath = StrUtil.replace(filePath, "${module}", table.getModuleName());
        filePath = StrUtil.replace(filePath, "${lowerCamelCase}", StrUtil.lowerFirst(wordName));
        filePath = StrUtil.replace(filePath, "${upperCamelCase}", StrUtil.upperFirst(wordName));
        filePath = StrUtil.replace(filePath, "${snakeCase}", StrUtil.toSymbolCase(wordName, '_'));
        filePath = StrUtil.replace(filePath, "${kabadCase}", StrUtil.toSymbolCase(wordName, '-'));

        String fileName = templateCodeItemEntity.getFileName();
        fileName = StrUtil.replace(fileName, "${package}", packageName);
        fileName = StrUtil.replace(fileName, "${module}", table.getModuleName());
        fileName = StrUtil.replace(fileName, "${lowerCamelCase}", StrUtil.lowerFirst(wordName));
        fileName = StrUtil.replace(fileName, "${upperCamelCase}", StrUtil.upperFirst(wordName));
        fileName = StrUtil.replace(fileName, "${snakeCase}", StrUtil.toSymbolCase(wordName, '_'));
        fileName = StrUtil.replace(fileName, "${kabadCase}", StrUtil.toSymbolCase(wordName, '-'));
        fileType.set("fileName", fileName);
        fileType.set("fileType", templateCodeItemEntity.getFileType());
        fileType.set("filePath", projectPath + filePath + fileName);
        fileType.set("fileContent", GenUtils.generateCode(table, templateEntity, templateCodeItemEntity, templateCodeItemEntities));
        fileType.set("checked", true);
        return fileType;
    }

    public static void buildIsBase(GenTableColumnEntity column, Map<String, TemplateColumnEntity> templateBaseClassItemMap) {
        TemplateColumnEntity templateBaseClassItemEntity = templateBaseClassItemMap.get(column.getFieldName().toUpperCase());
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
            }
            return;
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
            return;
        }

    }
}
