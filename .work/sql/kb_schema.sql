-- ============================================================================
-- 知识库模块 数据库表结构
-- 对应 PRD：知识库模块-需求文档.md v1.0
-- 一期范围：F1 知识库管理 / F2 文档管理 / F3 文档解析 / F4 自动分段+向量化 / F8 删除清理
-- 执行方式：MySQL 8.x 直接执行（mopga-gen 库）
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. 知识库表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_kb_base`;
CREATE TABLE `t_kb_base` (
    `base_id`         BIGINT      NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
    `name`            VARCHAR(100) NOT NULL COMMENT '知识库名称(唯一)',
    `description`     TEXT        DEFAULT NULL COMMENT '知识库描述',
    `doc_count`       INT         NOT NULL DEFAULT 0 COMMENT '文档数量(冗余)',
    `create_user_id`  BIGINT      DEFAULT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50) DEFAULT NULL COMMENT '创建人名称',
    `create_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`base_id`),
    UNIQUE KEY `uk_kb_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识库-知识库';

-- ----------------------------------------------------------------------------
-- 2. 文档表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_kb_document`;
CREATE TABLE `t_kb_document` (
    `document_id`      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '文档ID',
    `base_id`          BIGINT       NOT NULL COMMENT '所属知识库ID',
    `file_name`        VARCHAR(255) NOT NULL COMMENT '原始文件名',
    `file_url`         VARCHAR(500) DEFAULT NULL COMMENT '文件存储路径/对象存储地址',
    `file_size`        BIGINT       DEFAULT 0 COMMENT '文件字节数',
    `file_type`        VARCHAR(20)  NOT NULL COMMENT '扩展名: txt/md/pdf/docx/html',
    `status`           TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:0-上传中转 1-待解析 2-解析中 3-已解析 4-分段中 5-向量化中 6-已生效 7-解析失败 8-向量化失败',
    `queue_order`      INT          NOT NULL DEFAULT 0 COMMENT '待解析队列顺序号',
    `chunk_count`      INT          NOT NULL DEFAULT 0 COMMENT '分段数量',
    `parsed_content`   LONGTEXT     DEFAULT NULL COMMENT '解析后的纯文本',
    `error_message`    VARCHAR(1000) DEFAULT NULL COMMENT '失败原因',
    `parse_retry_count` INT         NOT NULL DEFAULT 0 COMMENT '解析重试次数',
    `create_user_id`   BIGINT       DEFAULT NULL COMMENT '创建人ID',
    `create_user_name` VARCHAR(50)  DEFAULT NULL COMMENT '创建人名称',
    `create_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`document_id`),
    KEY `idx_kb_doc_base` (`base_id`),
    KEY `idx_kb_doc_status` (`status`),
    KEY `idx_kb_doc_queue` (`status`, `queue_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识库-文档';

-- ----------------------------------------------------------------------------
-- 3. 分段表
-- ----------------------------------------------------------------------------
DROP TABLE IF EXISTS `t_kb_segment`;
CREATE TABLE `t_kb_segment` (
    `segment_id`      BIGINT      NOT NULL AUTO_INCREMENT COMMENT '分段ID',
    `document_id`     BIGINT      NOT NULL COMMENT '所属文档ID',
    `base_id`         BIGINT      NOT NULL COMMENT '所属知识库ID(冗余,便于检索过滤)',
    `segment_index`   INT         NOT NULL COMMENT '文档内序号',
    `content`         LONGTEXT    NOT NULL COMMENT '分段文本内容',
    `character_count` INT         NOT NULL DEFAULT 0 COMMENT '内容长度',
    `vector_id`       VARCHAR(64) DEFAULT NULL COMMENT 'Weaviate 对象ID',
    `status`          TINYINT     NOT NULL DEFAULT 0 COMMENT '状态:0-未向量化 1-已向量化 2-向量化失败',
    `create_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`segment_id`),
    KEY `idx_kb_seg_doc` (`document_id`),
    KEY `idx_kb_seg_base` (`base_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='知识库-分段';
