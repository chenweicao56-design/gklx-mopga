package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 需求管理-需求新建
 *
 * @author gklx
 */
@Data
public class RequirementAddForm {

    @Schema(description = "归属项目ID")
    @NotNull(message = "请选择归属项目")
    private Long projectId;

    @Schema(description = "需求标题")
    @NotBlank(message = "需求标题不能为空")
    @Length(max = 100, message = "需求标题最多100字符")
    private String title;

    @Schema(description = "富文本需求描述")
    private String content;

    @Schema(description = "验收标准列表")
    private List<RequirementAcceptanceForm> acceptanceCriteria;

    @Schema(description = "关联工程ID集合")
    private List<Long> repoIdList;

    @Schema(description = "创建人ID", hidden = true)
    private Long createBy;

    @Schema(description = "创建人名称", hidden = true)
    private String createByName;
}
