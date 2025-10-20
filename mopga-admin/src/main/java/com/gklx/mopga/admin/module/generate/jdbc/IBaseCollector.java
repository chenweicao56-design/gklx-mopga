package com.gklx.mopga.admin.module.generate.jdbc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gklx.mopga.admin.module.generate.domain.entity.DatabaseEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.GenTableColumnEntity;
import com.gklx.mopga.admin.module.generate.domain.entity.TableEntity;
import com.gklx.mopga.admin.module.generate.domain.form.TableQueryForm;

import java.util.List;

public interface IBaseCollector {

    IPage<TableEntity> selectDbTableList(IPage<TableEntity> page, DatabaseEntity database, TableQueryForm params);


    List<GenTableColumnEntity> selectDbTableColumnsByName(DatabaseEntity database, String tableName);


}
