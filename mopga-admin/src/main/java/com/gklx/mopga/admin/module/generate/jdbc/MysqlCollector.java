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

import java.util.*;

@Service("mysql")
public class MysqlCollector extends JdbcManager implements IBaseCollector {

    private final String databaseType = "3306";

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
            whereSql = whereSql + String.format(" AND TABLE_NAME in (%s)", getSqlStringForIn(Arrays.asList(tableNameArr))); //
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
        List<Map<String, Object>> maps = this.queryMultipleRows(database, sqlItem.getSql(), Collections.singletonList(tableName));
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
}
