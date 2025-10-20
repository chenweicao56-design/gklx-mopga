package com.gklx.mopga.admin.module.generate.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源表 分页查询表单
 *
 * @Author gklx
 * @Date 2025-09-19 13:15:40
 * @Copyright gklx
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class DatabaseQueryForm extends PageParam {

    @Schema(description = "名称查询")
    private String databaseName;

}
