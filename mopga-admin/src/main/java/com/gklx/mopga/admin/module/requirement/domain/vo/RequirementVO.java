package com.gklx.mopga.admin.module.requirement.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 需求管理-需求 VO
 *
 * @author gklx
 */
@Data
public class RequirementVO {

    @Schema(description = "需求ID")
    private Long requirementId;

    @Schema(description = "需求编号")
    private String reqNumber;

    @Schema(description = "归属项目ID")
    private Long projectId;

    @Schema(description = "归属项目名称")
    private String projectName;

    @Schema(description = "归属项目编码")
    private String projectCode;

    @Schema(description = "需求标题")
    private String title;

    @Schema(description = "富文本需求描述")
    private String content;

    @Schema(description = "验收标准(JSON字符串)")
    private String acceptanceCriteria;

    @Schema(description = "验收标准列表")
    private List<RequirementAcceptanceVO> acceptanceCriteriaList;

    @Schema(description = "状态:1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败")
    private Integer status;

    @Schema(description = "状态描述")
    private String statusDesc;

    @Schema(description = "关联工程ID集合")
    private List<Long> repoIdList;

    @Schema(description = "关联工程信息")
    private List<RequirementRepoVO> repoList;

    @Schema(description = "创建人ID")
    private Long createBy;

    @Schema(description = "创建人名称")
    private String createByName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
