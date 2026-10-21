-- ============================================================================
-- 需求管理功能 数据库表结构
-- 对应 PRD：需求管理-需求文档.md v1.0
-- 执行方式：MySQL 8.x 直接执行
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. 项目管理表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_requirement_project`;
CREATE TABLE `t_requirement_project` (
    `project_id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `project_name`    VARCHAR(50)  NOT NULL COMMENT '项目名称',
    `project_code`    VARCHAR(32)  NOT NULL COMMENT '项目编码(唯一,用于需求编号前缀)',
    `description`     VARCHAR(500) DEFAULT NULL COMMENT '项目描述',
    `owner`           VARCHAR(50)  DEFAULT NULL COMMENT '负责人姓名',
    `status`          TINYINT      NOT NULL DEFAULT 0 COMMENT '状态:0-启用 1-停用',
    `create_user_id`  BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人名称',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`project_id`),
    UNIQUE KEY `uk_project_code` (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='需求管理-项目';

-- ----------------------------------------------------------------------------
-- 2. 工程(git仓库)管理表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_requirement_repo`;
CREATE TABLE `t_requirement_repo` (
    `repo_id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '工程ID',
    `project_id`      BIGINT       NOT NULL COMMENT '归属项目ID',
    `repo_name`       VARCHAR(50)  NOT NULL COMMENT '工程/仓库名称',
    `repo_url`        VARCHAR(255) NOT NULL COMMENT 'git仓库地址(http/ssh)',
    `default_branch`  VARCHAR(50)  DEFAULT 'master' COMMENT '默认分支',
    `local_path`      VARCHAR(255) DEFAULT NULL COMMENT '本地克隆路径',
    `description`     VARCHAR(500) DEFAULT NULL COMMENT '描述',
    `status`          TINYINT      NOT NULL DEFAULT 0 COMMENT '状态:0-启用 1-停用',
    `create_user_id`  BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人名称',
    `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`repo_id`),
    UNIQUE KEY `uk_project_repo_name` (`project_id`, `repo_name`),
    KEY `idx_repo_project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='需求管理-工程(git仓库)';

-- ----------------------------------------------------------------------------
-- 3. 需求管理表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_requirement`;
CREATE TABLE `t_requirement` (
    `requirement_id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '需求ID',
    `req_number`          VARCHAR(32)  NOT NULL COMMENT '需求编号(唯一,格式PREFIX+YYYYMMDD-XX)',
    `project_id`          BIGINT       NOT NULL COMMENT '归属项目ID',
    `title`               VARCHAR(100) NOT NULL COMMENT '需求标题',
    `content`             TEXT         COMMENT '富文本需求描述',
    `acceptance_criteria` JSON         DEFAULT NULL COMMENT '验收标准列表(JSON数组)',
    `status`              TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:1-待开发 2-开发中 3-测试中 4-已完成 5-已交付 6-失败',
    `create_by`           BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `create_by_name`      VARCHAR(50)  DEFAULT NULL COMMENT '创建人名称',
    `create_time`         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`requirement_id`),
    UNIQUE KEY `uk_req_number` (`req_number`),
    KEY `idx_req_project` (`project_id`),
    KEY `idx_req_status` (`status`),
    KEY `idx_req_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='需求管理-需求';

-- ----------------------------------------------------------------------------
-- 4. 需求-工程 关联表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_requirement_repo_rel`;
CREATE TABLE `t_requirement_repo_rel` (
    `id`             BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `requirement_id` BIGINT   NOT NULL COMMENT '需求ID',
    `repo_id`        BIGINT   NOT NULL COMMENT '工程ID',
    `create_time`    DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_req_repo` (`requirement_id`, `repo_id`),
    KEY `idx_rel_requirement` (`requirement_id`),
    KEY `idx_rel_repo` (`repo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='需求管理-需求工程关联';

-- ----------------------------------------------------------------------------
-- 5. 需求状态流转历史表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_requirement_status_log`;
CREATE TABLE `t_requirement_status_log` (
    `log_id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `requirement_id`  BIGINT      NOT NULL COMMENT '需求ID',
    `from_status`     TINYINT     DEFAULT NULL COMMENT '原状态',
    `to_status`       TINYINT     NOT NULL COMMENT '目标状态',
    `operator`        BIGINT      DEFAULT NULL COMMENT '操作用户ID',
    `operator_name`   VARCHAR(50) DEFAULT NULL COMMENT '操作用户名称',
    `source`          TINYINT     NOT NULL DEFAULT 1 COMMENT '来源:1-页面操作 2-MCP调用',
    `remark`          VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `create_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`log_id`),
    KEY `idx_status_req` (`requirement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='需求管理-需求状态流转历史';
