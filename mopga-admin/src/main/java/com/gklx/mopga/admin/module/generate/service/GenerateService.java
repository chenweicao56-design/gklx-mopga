package com.gklx.mopga.admin.module.generate.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gklx.mopga.admin.module.generate.domain.entity.*;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVO;
import com.gklx.mopga.admin.module.generate.domain.vo.TemplateVO;
import com.gklx.mopga.admin.module.generate.jdbc.IBaseCollector;
import com.gklx.mopga.admin.module.generate.jdbc.JdbcManager;
import com.gklx.mopga.admin.module.generate.manager.*;
import com.gklx.mopga.admin.module.generate.util.GenUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.VelocityContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据源表 Service
 *
 * @Author gklx
 * @Date 2025-09-05 09:07:47
 * @Copyright 1
 */
@Slf4j
@Service
public class GenerateService {


    @Resource
    private DatabaseManager databaseManager;
    @Resource
    private GenTableColumnManager tableColumnManager;
    @Resource
    private TableManager tableManager;
    @Resource
    private TableService tableService;
    @Resource
    private GenTableColumnService genTableColumnService;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private TemplateService templateService;
    @Resource
    private TemplateManager templateManager;
    @Resource
    private TemplateCodeItemService templateCodeItemService;
    @Resource
    private TemplateColumnService templateColumnService;

    @Resource
    private MappingDataManager mappingDataManager;

    @Transactional(rollbackFor = Exception.class)
    public Boolean syncTable(Long databaseId, Boolean containColumn, String tableNames) {
        DatabaseEntity database = databaseManager.getById(databaseId);
        IBaseCollector collector = applicationContext.getBean(database.getDatabaseType(), IBaseCollector.class);
        long size = 10;
        long current = 0;
        long total = 1;
        IPage<TableEntity> page = new Page<>();
        TableQueryForm tableQueryForm = new TableQueryForm();
        tableQueryForm.setTableNames(tableNames);
        while (current * size < total) {
            current++;
            page.setSize(size);
            page.setCurrent(current);
            IPage<TableEntity> genTableIPage = collector.selectDbTableList(page, database, tableQueryForm);
            total = genTableIPage.getTotal();
            List<TableEntity> records = genTableIPage.getRecords();
            for (int i = 0; i < records.size(); i++) {
                TableEntity table = records.get(i);
                TableVO tableVO = tableService.getByName(table.getTableName());
                if (ObjUtil.isNotNull(tableVO)) {
                    log.info("表名：{}已存在，跳过同步", table.getTableName());
                } else {
                    table.setSort((int) (i + 1 + size * (current - 1)));
                    table.setDatabaseId(databaseId);
                    table.setIsPhysicallyDeleted(database.getIsPhysicallyDeleted());
                    table.setBackendAuthor(database.getBackendAuthor());
                    table.setBackendDate(ObjUtil.isNotNull(database.getBackendDate()) ? database.getBackendDate() : LocalDateTime.now());
                    table.setFrontAuthor(database.getFrontAuthor());
                    table.setFrontDate(ObjUtil.isNotNull(database.getFrontDate()) ? database.getFrontDate() : LocalDateTime.now());
                    table.setCopyright(database.getCopyright());
                    String moduleName = database.getModuleName();
                    table.setModuleName(moduleName);
                    table.setPackageName(StrUtil.isEmpty(moduleName) ? database.getPackageName() : database.getPackageName() + "." + moduleName.toLowerCase());
                    table.setExtendedData(database.getTableExtendedData());
                    String tablePrefixs = database.getTablePrefix();
                    if (StrUtil.isNotBlank(tablePrefixs)) {
                        Optional<String> prefixOpt = Arrays.stream(StrUtil.splitToArray(tablePrefixs, ","))
                                .filter(e -> StrUtil.startWith(table.getTableName(), e))
                                .findFirst();
                        String tablePrefix = prefixOpt.orElse(null);
                        table.setTablePrefix(tablePrefix);
                        String wordName = StrUtil.removePrefix(table.getTableName(), tablePrefix);
                        table.setWordName(StrUtil.toCamelCase(wordName));
                    } else {
                        table.setWordName(StrUtil.toCamelCase(table.getWordName()));
                    }
                    table.setIsPage(database.getIsPage());
                    table.setIsDetail(database.getIsDetail());
                    table.setIsUpdate(database.getIsUpdate());
                    table.setIsDelete(database.getIsDelete());
                    table.setIsBatchDelete(database.getIsBatchDelete());
                    table.setEditComponent(database.getEditComponent());
                    table.setFormCountLine(database.getFormCountLine());
                    tableManager.save(table);
                    if (containColumn) {
                        syncTableColumn(table.getTableId());
                    }
                }
            }
        }
        return true;

    }

    public Boolean syncTableColumn(List<Long> tableIds) {
        tableIds.forEach(this::syncTableColumn);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean syncTableColumn(Long tableId) {
        TableEntity table = tableManager.getById(tableId);
        DatabaseEntity database = databaseManager.getById(table.getDatabaseId());
        TemplateVO templateVO = templateService.getById(database.getTemplateId());

        List<MappingDataEntity> mappingDataEntities = mappingDataManager.listByMappingCode(database.getLanguageType().toUpperCase());
        Map<String, MappingDataEntity> defaultMappingMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(mappingDataEntities)) {
            defaultMappingMap = mappingDataEntities.stream().collect(Collectors.toMap(e -> e.getDatabaseFieldType().toUpperCase(), e -> e));
        }
        Map<String, TemplateColumnEntity> templateColumnMap = templateVO.getTemplateColumns().stream().collect(Collectors.toMap(e -> e.getFieldName().toUpperCase(), e -> e));
        Map<String, TemplateMappingItemEntity> templateMappingItemEntityMap = templateVO.getTemplateMappingItems().stream().collect(Collectors.toMap(e -> e.getDatabaseColumnType().toUpperCase(), e -> e));
        IBaseCollector collector = applicationContext.getBean(database.getDatabaseType(), IBaseCollector.class);
        List<GenTableColumnEntity> columns = collector.selectDbTableColumnsByName(database, table.getTableName());
        for (int i = 0; i < columns.size(); i++) {
            GenTableColumnEntity column = columns.get(i);
            GenTableColumnEntity columnEntity = genTableColumnService.getByName(tableId, column.getColumnName());
            if (ObjUtil.isNotNull(columnEntity)) {
                log.info("字段名：{}已存在，跳过同步", column.getColumnName());
            } else {
                column.setSort(i + 1);
                column.setTableId(tableId);
                column.setDatabaseId(database.getId());
                column.setFieldName(StrUtil.lowerFirst(StrUtil.toCamelCase(column.getColumnName())));
                column.setFieldComment(column.getColumnComment());
                GenUtils.buildIsBase(column, templateColumnMap);
                GenUtils.buildFileType(column, templateMappingItemEntityMap, defaultMappingMap);
                if (StrUtil.isEmpty(column.getFieldType())) {
                    log.error("未找到字段类型：{}:{}", table.getTableName(), column.getFieldName());
                }
                column.setIsRequired(column.getIsNull());
                column.setIsInsert(!column.getIsBase());
                column.setIsUpdate(column.getIsPk() || !column.getIsBase());
                column.setIsWhere(false);
                if (column.getIsPk() || column.getIsBase()) {
                    column.setIsTable(false);
                } else {
                    column.setIsTable(true);
                }
                column.setWhereType(null);
                column.setExtendedData(database.getColumnExtendedData());
                tableColumnManager.save(column);
            }
        }
        return true;
    }

    public List<JSONObject> preview(Long tableId) {
        TableVO table = tableService.getById(tableId);
        DatabaseEntity database = databaseManager.getById(table.getDatabaseId());
        TemplateEntity templateEntity = templateManager.getById(database.getTemplateId());
        List<TemplateCodeItemEntity> templateCodeItemEntities = templateCodeItemService.listByTemplateId(database.getTemplateId());
        Map<String, List<TemplateCodeItemEntity>> collect = templateCodeItemEntities.stream().collect(Collectors.groupingBy(TemplateCodeItemEntity::getFileType));

        List<JSONObject> res = new ArrayList<>();
        for (Map.Entry<String, List<TemplateCodeItemEntity>> entry : collect.entrySet()) {

            JSONObject fileType = new JSONObject();
            fileType.set("label", entry.getKey());
            fileType.set("value", entry.getKey());
            fileType.set("files", entry.getValue().stream().map(e -> GenUtils.buildFile(table, templateEntity, e, templateCodeItemEntities)).toList());
            res.add(fileType);
        }
        return res;
    }

    public String createTable(TableVO table, boolean isSync) {
        DatabaseEntity database = databaseManager.getById(table.getDatabaseId());
        IBaseCollector collector = applicationContext.getBean(database.getDatabaseType(), IBaseCollector.class);
        String createTableSql = buildCreateTableSqlTemplate(table);
        if (collector instanceof JdbcManager manager) {
//            manager.executeUpdate(database, createTableSql);
            manager.executeBatch(database, Arrays.stream(createTableSql.trim().split(";")).toList());
        }
        if (isSync) {
            syncTable(table.getDatabaseId(), true, table.getTableName());
        }
        return createTableSql;
    }

    public String buildCreateTableSqlTemplate(TableVO table) {
        DatabaseEntity database = databaseManager.getById(table.getDatabaseId());
        TemplateCodeItemEntity templateCodeItemEntity = templateCodeItemService.getCreateTableTemplateByTemplateId(database.getTemplateId());
        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", table.getTableComment());
        List<GenTableColumnVo> columns = table.getColumns();

        //补充

        if (CollectionUtil.isNotEmpty(columns)) {
            List<GenTableColumnEntity> queryColumns = new ArrayList<>();
            for (GenTableColumnVo column : columns) {
                if (column.getIsPk()) {
                    velocityContext.put("primaryKeyColumnName", column.getColumnName());
                    velocityContext.put("primaryKeyFieldType", column.getFieldType());
                }
            }
            velocityContext.put("queryColumns", queryColumns);
        }
        velocityContext.put("columns", table.getColumns());

        StringWriter writer = new StringWriter();
        GenUtils.velocityEngine.evaluate(velocityContext, writer, templateCodeItemEntity.getFileName(), new StringReader(templateCodeItemEntity.getContent()));
        return writer.toString();
    }


}
