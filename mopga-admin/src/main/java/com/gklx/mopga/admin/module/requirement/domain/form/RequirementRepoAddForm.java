package com.gklx.mopga.admin.module.requirement.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 需求管理-工程(git仓库)新建
 *
 * @author gklx
 */
@Data
public class RequirementRepoAddForm {

    @Schema(description = "归属项目ID")
    @NotNull(message = "归属项目不能为空")
    private Long projectId;

    @Schema(description = "工程/仓库名称")
    @NotBlank(message = "工程名称不能为空")
    @Length(max = 50, message = "工程名称最多50字符")
    private String repoName;

    @Schema(description = "git仓库地址(http/ssh)")
    @NotBlank(message = "git仓库地址不能为空")
    @Length(max = 255, message = "git仓库地址最多255字符")
    private String repoUrl;

    @Schema(description = "默认分支")
    @Length(max = 50, message = "默认分支最多50字符")
    private String defaultBranch;

    @Schema(description = "本地克隆路径")
    @Length(max = 255, message = "本地克隆路径最多255字符")
    private String localPath;

    @Schema(description = "描述")
    @Length(max = 500, message = "描述最多500字符")
    private String description;

    @Schema(description = "创建人ID", hidden = true)
    private Long createUserId;

    @Schema(description = "创建人名称", hidden = true)
    private String createUserName;
}
