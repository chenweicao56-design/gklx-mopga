package com.gklx.mopga.admin.module.requirement.domain.form;

import com.gklx.mopga.base.common.domain.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-工程(git仓库)查询
 *
 * @author gklx
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RequirementRepoQueryForm extends PageParam {

    @Schema(description = "归属项目ID")
    private Long projectId;

    @Schema(description = "工程名称(关键字)")
    @Length(max = 50, message = "工程名称最多50字符")
    private String repoName;

    @Schema(description = "状态:0-启用 1-停用")
    private Integer status;
}
