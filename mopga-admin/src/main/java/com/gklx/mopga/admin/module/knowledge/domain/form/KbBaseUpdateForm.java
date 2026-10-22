package com.gklx.mopga.admin.module.knowledge.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 知识库-更新
 *
 * @author gklx
 */
@Data
public class KbBaseUpdateForm {

    @Schema(description = "知识库ID")
    @NotNull(message = "知识库ID不能为空")
    private Long baseId;

    @Schema(description = "知识库名称")
    @NotNull(message = "知识库名称不能为空")
    @Length(max = 100, message = "知识库名称最多100字符")
    private String name;

    @Schema(description = "知识库描述")
    @Length(max = 1000, message = "知识库描述最多1000字符")
    private String description;
}
