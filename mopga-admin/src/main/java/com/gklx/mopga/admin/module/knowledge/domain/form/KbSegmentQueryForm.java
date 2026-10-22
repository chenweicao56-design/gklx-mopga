package com.gklx.mopga.admin.module.knowledge.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 知识库-分段查询
 *
 * @author gklx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KbSegmentQueryForm extends PageParam {

    @Schema(description = "文档ID")
    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    @Schema(description = "知识库ID")
    @NotNull(message = "知识库ID不能为空")
    private Long baseId;
}
