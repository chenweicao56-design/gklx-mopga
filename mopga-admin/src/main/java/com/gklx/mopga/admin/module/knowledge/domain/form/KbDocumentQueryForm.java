package com.gklx.mopga.admin.module.knowledge.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 知识库-文档查询
 *
 * @author gklx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KbDocumentQueryForm extends PageParam {

    @Schema(description = "知识库ID")
    @NotNull(message = "知识库ID不能为空")
    private Long baseId;

    @Schema(description = "文件名(关键字)")
    @Length(max = 255, message = "文件名最多255字符")
    private String keyword;

    @Schema(description = "状态")
    private Integer status;
}
