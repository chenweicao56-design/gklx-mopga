package com.gklx.mopga.admin.module.knowledge.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 知识库-查询
 *
 * @author gklx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KbBaseQueryForm extends PageParam {

    @Schema(description = "知识库名称(关键字)")
    @Length(max = 100, message = "知识库名称最多100字符")
    private String keyword;
}
