package com.gklx.mopga.admin.util;

import com.gklx.mopga.admin.module.generate.domain.vo.DatabaseVo;
import com.gklx.mopga.admin.module.generate.domain.vo.GenTableColumnVo;
import com.gklx.mopga.admin.module.generate.domain.vo.TableVo;

import java.util.List;

public class DbUtil {

    private String formatDatabase(DatabaseVo databaseVo) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("【%s】 %s, %s\n", databaseVo.getId(), databaseVo.getDatabaseName(), databaseVo.getDatabaseName()));
        sb.append("【Schema】\n");
        List<TableVo> tables = databaseVo.getTables();
        for (TableVo table : tables) {
            sb.append(String.format("# Table: %s.%s, %s\n", databaseVo.getSchemaName(), table.getTableName(), table.getTableComment()));
            sb.append("[\n");
            List<GenTableColumnVo> columns = table.getColumns();
            for (GenTableColumnVo column : columns) {
                String dictType = column.getDictType();
//
//                if (CollectionUtil.isNotEmpty(dicts)) {
//                    String remark = String.format(",字典类型(%s)", CollUtil.join(dicts, ","));
//                    sb.append(String.format("(%s:%s, %s%s),\n", column.getColumnName(), column.getColumnType(), column.getColumnComment(), remark));
//
//                } else {
//                }
                sb.append(String.format("(%s:%s, %s),\n", column.getColumnName(), column.getColumnType(), column.getColumnComment()));

            }
            sb.append("]\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}
