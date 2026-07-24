-- ============================================================
-- Skill 管理平台 - 数据库表结构脚本
-- 适用：MySQL 8.x
-- 规范：BIGINT 自增主键 / 字段默认 NOT NULL / 审计字段齐全 /
--       逻辑删除 deleted_flag / 引擎 InnoDB / 字符集 utf8mb4
-- 说明：
--   - 字典字段（category 分类、file_type 文件类型、status 状态）
--     复用已有 sys_dict 字典表，本脚本不重复创建。
--   - status 状态枚举：0-草稿, 1-已上线, 2-已下线。
--   - 目录不单独建表，由 skill_file.file_path 前缀派生。
--   - 标签存 skill.tags（JSON 数组），不单独建关联表，建多值索引支持高效过滤。
--   - 审计日志表暂不创建。
-- ============================================================

-- ------------------------------------------------------------
-- 7.1 技能主表
-- ------------------------------------------------------------
CREATE TABLE `skill` (
  `id`              BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name`            VARCHAR(128) NOT NULL COMMENT '技能名称（对应 SKILL.md frontmatter name，兼作引用标识）',
  `description`     VARCHAR(512) NOT NULL DEFAULT '' COMMENT '技能描述（对应 SKILL.md frontmatter description）',
  `category`        VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '分类（sys_dict 字典编码）',
  `tags`            JSON        NULL COMMENT '标签（JSON 数组，如 ["数据库","规范"]）',
  `status`          TINYINT      NOT NULL DEFAULT 0 COMMENT '状态: 0-草稿,1-已上线,2-已下线',
  `current_version` BIGINT       DEFAULT NULL COMMENT '当前线上生效版本ID（关联 skill_version.id）',
  `create_user_id`  BIGINT       NOT NULL COMMENT '创建人',
  `create_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id`  BIGINT       DEFAULT NULL COMMENT '更新人',
  `update_time`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag`    TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '删除状态: 0-未删,1-已删',
  PRIMARY KEY (`id`),
  KEY `idx_skill_status` (`status`),
  KEY `idx_skill_category` (`category`),
  KEY `idx_skill_tags` ((CAST(`tags` AS CHAR(64) ARRAY)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='技能主表';

-- ------------------------------------------------------------
-- 7.3 草稿文件表（目录由 file_path 前缀派生，不单独建表）
-- ------------------------------------------------------------
CREATE TABLE `skill_file` (
  `id`           BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `skill_id`     BIGINT      NOT NULL COMMENT '所属技能（关联 skill.id）',
  `file_path`    VARCHAR(512) NOT NULL COMMENT '文件相对路径（如 scripts/run.py，含文件名，目录为路径前缀）',
  `file_name`    VARCHAR(255) NOT NULL COMMENT '文件名',
  `file_type`    VARCHAR(32)  NOT NULL DEFAULT 'text' COMMENT '文件类型（sys_dict: markdown/json/text/code/其他）',
  `content`      LONGTEXT    COMMENT '文件内容（文本/代码，存库）',
  `content_size` INT         NOT NULL DEFAULT 0 COMMENT '内容字节大小',
  `is_entry`     TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否入口文件 SKILL.md: 0-否,1-是',
  `create_user_id` BIGINT    NOT NULL COMMENT '创建人',
  `create_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT    DEFAULT NULL COMMENT '更新人',
  `update_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_skill_file` (`skill_id`, `file_path`),
  KEY `idx_skill_file_path` (`skill_id`, `file_path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='技能草稿文件表';

-- ------------------------------------------------------------
-- 7.4 版本表
-- ------------------------------------------------------------
CREATE TABLE `skill_version` (
  `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `skill_id`    BIGINT      NOT NULL COMMENT '所属技能（关联 skill.id）',
  `version_no`  VARCHAR(32) NOT NULL COMMENT '版本号（语义化 MAJOR.MINOR.PATCH 或自增快照号）',
  `remark`      VARCHAR(512) NOT NULL DEFAULT '' COMMENT '版本说明/变更摘要',
  `is_released` TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否曾上线: 0-否,1-是',
  `create_user_id` BIGINT   NOT NULL COMMENT '创建人',
  `create_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT   DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_skill_version` (`skill_id`, `version_no`),
  KEY `idx_skill_version_skill` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='技能版本表';

-- ------------------------------------------------------------
-- 7.5 版本文件快照表（上线时由 skill_file 全量复制生成，不可变）
-- ------------------------------------------------------------
CREATE TABLE `skill_version_file` (
  `id`           BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `version_id`   BIGINT      NOT NULL COMMENT '所属版本（关联 skill_version.id）',
  `file_path`    VARCHAR(512) NOT NULL COMMENT '文件相对路径（快照时刻）',
  `file_name`    VARCHAR(255) NOT NULL COMMENT '文件名',
  `file_type`    VARCHAR(32) NOT NULL DEFAULT 'text' COMMENT '文件类型',
  `content`      LONGTEXT    COMMENT '文件内容快照（不可变副本）',
  `content_size` INT         NOT NULL DEFAULT 0 COMMENT '内容字节大小',
  `is_entry`     TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '是否入口文件 SKILL.md',
  `create_time`  DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted_flag` TINYINT(1)  NOT NULL DEFAULT 0 COMMENT '删除状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_skill_vf` (`version_id`, `file_path`),
  KEY `idx_skill_vf_version` (`version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='技能版本文件快照表';
