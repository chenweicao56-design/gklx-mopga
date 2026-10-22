package com.gklx.mopga.admin.module.knowledge.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 知识库-文档重命名
 *
 * @author gklx
 */
@Data
public class KbDocumentRenameForm {

    @Schema(description = "文档ID")
    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    @Schema(description = "新文件名")
    @NotBlank(message = "文件名不能为空")
    @Length(max = 255, message = "文件名最多255字符")
    private String fileName;
}
