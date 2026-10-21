package com.gklx.mopga.admin.module.requirement.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 需求管理-需求工程关联实体
 *
 * @author gklx
 */
@Data
@TableName("t_requirement_repo_rel")
public class RequirementRepoRelEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 需求ID
     */
    private Long requirementId;

    /**
     * 工程ID
     */
    private Long repoId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
