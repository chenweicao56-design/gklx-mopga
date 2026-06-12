-- 文档管理系统 SQL 脚本
-- 生成时间: 2026-06-12

-- ========================
-- 1. 文档主表
-- ========================
CREATE TABLE `doc_document` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除状态:0-未删除,1-已删除',
  `document_name` VARCHAR(100) NOT NULL COMMENT '文档名称',
  `document_content` TEXT NOT NULL COMMENT '文档内容',
  `file_type` VARCHAR(20) NOT NULL COMMENT '文件类型:markdown、json、text、code',
  `doc_type` VARCHAR(20) NOT NULL COMMENT '文档类型:技术文档、用户手册、配置文件',
  `version_no` VARCHAR(20) NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_doc_document_create_user` (`create_user_id`),
  KEY `idx_doc_document_doc_type` (`doc_type`),
  KEY `idx_doc_document_file_type` (`file_type`),
  KEY `idx_doc_document_deleted` (`deleted_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档主表';

-- ========================
-- 2. 文档历史版本表
-- ========================
CREATE TABLE `doc_document_version` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `document_id` BIGINT NOT NULL COMMENT '文档ID',
  `version_no` VARCHAR(20) NOT NULL COMMENT '版本号',
  `document_content` TEXT NOT NULL COMMENT '文档内容',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除状态:0-未删除,1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_doc_version_document` (`document_id`),
  KEY `idx_doc_version_version` (`version_no`),
  KEY `idx_doc_version_deleted` (`deleted_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档历史版本表';

-- ========================
-- 3. 文档标签关系表
-- ========================
CREATE TABLE `doc_document_tag_rel` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `document_id` BIGINT NOT NULL COMMENT '文档ID',
  `tag_code` VARCHAR(50) NOT NULL COMMENT '标签编码',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_doc_tag_rel` (`document_id`, `tag_code`),
  KEY `idx_doc_tag_rel_document` (`document_id`),
  KEY `idx_doc_tag_rel_tag` (`tag_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档标签关系表';

-- ========================
-- 4. 系统字典表
-- ========================
CREATE TABLE `sys_dict` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dict_type` VARCHAR(50) NOT NULL COMMENT '字典类型:DOCUMENT_TAG',
  `dict_code` VARCHAR(50) NOT NULL COMMENT '字典编码',
  `dict_label` VARCHAR(100) NOT NULL COMMENT '字典标签',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '删除状态:0-未删除,1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sys_dict_type_code` (`dict_type`, `dict_code`),
  KEY `idx_sys_dict_type` (`dict_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统字典表';

-- ========================
-- 5. 初始化标签数据
-- ========================
INSERT INTO `sys_dict` (`dict_type`, `dict_code`, `dict_label`, `sort`) VALUES
('DOCUMENT_TAG', 'feature', '功能特性', 1),
('DOCUMENT_TAG', 'bug', '缺陷修复', 2),
('DOCUMENT_TAG', 'performance', '性能优化', 3),
('DOCUMENT_TAG', 'security', '安全相关', 4),
('DOCUMENT_TAG', 'api', 'API文档', 5),
('DOCUMENT_TAG', 'config', '配置说明', 6);
