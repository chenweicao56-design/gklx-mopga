-- ============================================================
-- 项目需求管理平台 - 数据库表结构脚本
-- 需求编号(gklx)：2026101901
-- 适用：MySQL 8.x
-- 规范：BIGINT 自增主键 / 字段默认 NOT NULL / 审计字段齐全 /
--       逻辑删除 deleted_flag / 引擎 InnoDB / 字符集 utf8mb4
-- 说明：
--   - 字典字段（项目类型、需求类型、需求状态、需求优先级、任务状态等）
--     复用已有 sys_dict 字典表，本脚本不重复创建。
--   - status 状态枚举见各表注释。
--   - 需求支持标签，存 req_req.tags（JSON 数组），建多值索引。
--   - 工程管理指代码仓库/软件开发单元（repo），一个项目下可有多个工程。
--
-- 业务模块：
--   1. 项目管理     -> req_project
--   2. 工程管理     -> req_repo（代码仓库/软件工程）
--   3. 需求管理     -> req_req / req_req_repo（需求-工程多对多关联）
--
-- 核心场景支持：
--   - 智能体按「需求编号」「某天/某项目」检索需求 -> req_req 提供编号 + 多索引
--   - 智能体执行需求 -> 通过 @Tool 更新 req_req 状态与开发说明（不单建任务表）
--   - MCP 服务在代码层用 @Tool 实现（参照 GenerateMcp），不单独建表
-- ============================================================

-- ------------------------------------------------------------
-- 1. 项目表（项目管理）
-- 一个项目对应一个业务/研发单元，可包含多个工程（代码仓库）。
-- ------------------------------------------------------------
CREATE TABLE `req_project` (
  `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `project_no`      VARCHAR(64)  NOT NULL COMMENT '项目编号（用户可自定义，智能体按此引用，如 PRJ-2026-001）',
  `project_name`    VARCHAR(128) NOT NULL COMMENT '项目名称',
  `description`     VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '项目描述',
  `project_type`    VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '项目类型（sys_dict 字典编码）',
  `owner_user_id`   BIGINT       DEFAULT NULL COMMENT '项目负责人（关联员工表）',
  `start_date`      DATE         DEFAULT NULL COMMENT '计划开始日期',
  `end_date`        DATE         DEFAULT NULL COMMENT '计划结束日期',
  `status`          TINYINT      NOT NULL DEFAULT 0 COMMENT '状态: 0-筹备,1-进行中,2-已完成,3-已暂停,4-已归档',
  `tags`            JSON         NULL COMMENT '标签（JSON 数组，如 ["大数据","平台"]）',
  `remark`          VARCHAR(512) NOT NULL DEFAULT '' COMMENT '备注',
  `create_user_id`  BIGINT       NOT NULL COMMENT '创建人',
  `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id`  BIGINT       DEFAULT NULL COMMENT '更新人',
  `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag`    TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '删除状态: 0-未删,1-已删',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_req_project_no` (`project_no`),
  KEY `idx_req_project_name` (`project_name`),
  KEY `idx_req_project_status` (`status`),
  KEY `idx_req_project_owner` (`owner_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='项目表';

-- ------------------------------------------------------------
-- 2. 工程表（工程管理）
-- 指项目下的代码仓库/软件开发单元（repo）。一个项目多个工程。
-- 用于记录工程元信息，供 MCP/智能体定位代码目录与仓库。
-- ------------------------------------------------------------
CREATE TABLE `req_repo` (
  `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `project_id`      BIGINT        NOT NULL COMMENT '所属项目（关联 req_project.id）',
  `repo_name`       VARCHAR(128)  NOT NULL COMMENT '工程/仓库名称',
  `repo_type`       VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '工程类型（sys_dict: 前端/后端/服务/库等）',
  `repo_url`        VARCHAR(512)  NOT NULL DEFAULT '' COMMENT '代码仓库地址（git url）',
  `branch`          VARCHAR(128)  NOT NULL DEFAULT 'main' COMMENT '默认分支',
  `local_path`      VARCHAR(512)  NOT NULL DEFAULT '' COMMENT '本地代码目录（供智能体读写）',
  `description`     VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '工程描述',
  `tech_stack`      VARCHAR(512)  NOT NULL DEFAULT '' COMMENT '技术栈（如 Java/Spring Boot/MyBatis-Plus）',
  `status`          TINYINT       NOT NULL DEFAULT 0 COMMENT '状态: 0-草稿,1-启用,2-停用',
  `create_user_id`  BIGINT        NOT NULL COMMENT '创建人',
  `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id`  BIGINT        DEFAULT NULL COMMENT '更新人',
  `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag`    TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_req_repo` (`project_id`, `repo_name`),
  KEY `idx_req_repo_project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工程（代码仓库）表';

-- ------------------------------------------------------------
-- 3. 需求表（需求管理）
-- 需求是智能体开发的最小指令单元，通过「需求编号」被智能体引用。
-- req_no 是唯一业务编号，智能体按此精确检索。
-- 关联项目（project_id）实现「某项目需求」检索；工程通过多对多关联表 req_req_repo。
-- ------------------------------------------------------------
CREATE TABLE `req_req` (
  `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `project_id`      BIGINT        NOT NULL COMMENT '所属项目（关联 req_project.id）',
  `req_no`          VARCHAR(64)   NOT NULL COMMENT '需求编号（业务唯一，智能体按此引用，如 REQ-2026101901-001）',
  `req_title`       VARCHAR(255)  NOT NULL COMMENT '需求标题/一句话描述',
  `req_type`        VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '需求类型（sys_dict: 功能/优化/缺陷/重构/测试/文档/交付等）',
  `priority`        VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '优先级（sys_dict: P0紧急/P1高/P2中/P3低）',
  `status`          TINYINT       NOT NULL DEFAULT 0 COMMENT '状态: 0-待处理,1-开发中,2-测试中,3-已交付,4-已关闭,5-已驳回',
  `content`         LONGTEXT      COMMENT '需求详细描述（Markdown，供智能体读取开发）',
  `acceptance_criteria` LONGTEXT  COMMENT '验收标准（Markdown，供智能体自测与交付核验）',
  `tags`            JSON          NULL COMMENT '标签（JSON 数组）',
  `planned_date`    DATE          DEFAULT NULL COMMENT '计划完成日期（支持按某天检索需求）',
  `assigned_user_id` BIGINT       DEFAULT NULL COMMENT '负责人（程序员）',
  `parent_id`       BIGINT        DEFAULT NULL COMMENT '父需求ID（支持需求拆分，关联 req_req.id）',
  `source`          VARCHAR(32)   NOT NULL DEFAULT 'manual' COMMENT '来源: manual-手动录入,agent-智能体生成,mcp-外部导入',
  `create_user_id`  BIGINT        NOT NULL COMMENT '创建人',
  `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id`  BIGINT        DEFAULT NULL COMMENT '更新人',
  `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag`    TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_req_req_no` (`req_no`),
  KEY `idx_req_req_project` (`project_id`, `status`),
  KEY `idx_req_req_status` (`status`),
  KEY `idx_req_req_planned_date` (`planned_date`),
  KEY `idx_req_req_assignee` (`assigned_user_id`),
  KEY `idx_req_req_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='需求表';

-- ------------------------------------------------------------
-- 3.1 需求-工程关联表（多对多）
-- 一个需求可对应多个工程（req_req 与 req_repo 多对多），
-- 智能体开发时按此定位要改动的全部代码仓库。
-- ------------------------------------------------------------
CREATE TABLE `req_req_repo` (
  `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `req_id`          BIGINT        NOT NULL COMMENT '需求ID（关联 req_req.id）',
  `repo_id`         BIGINT        NOT NULL COMMENT '工程ID（关联 req_repo.id）',
  `main_flag`       TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '是否主工程: 0-否,1-是（同一需求至多一个主工程）',
  `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted_flag`    TINYINT(1)    NOT NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_req_req_repo` (`req_id`, `repo_id`),
  KEY `idx_req_req_repo_repo` (`repo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='需求-工程关联表';



