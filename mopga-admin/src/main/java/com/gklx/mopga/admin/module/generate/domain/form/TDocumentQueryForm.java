package com.gklx.mopga.admin.module.generate.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import com.gklx.mopga.base.common.domain.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档主表 分页查询表单
 *
 * @Author gklx
 * @Date 2026-06-12 09:54:33
 * @Copyright 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class TDocumentQueryForm extends PageParam {
}