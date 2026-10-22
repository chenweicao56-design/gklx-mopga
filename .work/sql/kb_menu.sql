-- =====================================================================
-- 知识库模块 - 菜单 & 权限 脚本 (mopga-gen 库)
--
-- 参照 requirement 模块(900-916)菜单范式编写：
--   menu_type: 1=目录 / 2=页面菜单 / 3=按钮权限
--   目录/页面菜单 perms 为 NULL；按钮 perms_type=1，api_perms=web_perms
-- 菜单ID 区间：1000-1013（执行前可核对是否被占用）
--
-- 页面结构：
--   /business/dataset/base/base-list.vue       知识库列表
--   /business/dataset/document/doc-list.vue    文档列表
--   /business/dataset/segment/segment-list.vue 分段列表
-- =====================================================================

-- 核对：SELECT menu_id, menu_name FROM t_menu WHERE menu_id BETWEEN 1000 AND 1013;

-- ---------------------------------------------------------------------
-- 1. 目录：知识库
-- ---------------------------------------------------------------------
INSERT INTO `t_menu`
(`menu_id`, `menu_name`, `menu_type`, `parent_id`, `sort`, `path`, `component`,
 `perms_type`, `api_perms`, `web_perms`, `icon`, `context_menu_id`,
 `frame_flag`, `frame_url`, `cache_flag`, `visible_flag`, `disabled_flag`,
 `deleted_flag`, `create_user_id`, `create_time`, `update_user_id`, `update_time`)
VALUES
(1000, '知识库', 1, 0, 90, '/business/dataset', NULL,
 NULL, NULL, NULL, 'DatabaseOutlined', NULL,
 0, NULL, 0, 1, 0,
 0, 1, NOW(), 1, NOW());

-- ---------------------------------------------------------------------
-- 2. 页面菜单
-- ---------------------------------------------------------------------
INSERT INTO `t_menu`
(`menu_id`, `menu_name`, `menu_type`, `parent_id`, `sort`, `path`, `component`,
 `perms_type`, `api_perms`, `web_perms`, `icon`, `context_menu_id`,
 `frame_flag`, `frame_url`, `cache_flag`, `visible_flag`, `disabled_flag`,
 `deleted_flag`, `create_user_id`, `create_time`, `update_user_id`, `update_time`)
VALUES
(1001, '知识库管理', 2, 1000, 1, '/business/dataset/base', '/business/dataset/base/base-list.vue',
 NULL, NULL, NULL, 'DatabaseOutlined', NULL,
 0, NULL, 0, 1, 0,
 0, 1, NOW(), 1, NOW()),
(1002, '文档管理', 2, 1000, 2, '/business/dataset/document', '/business/dataset/document/doc-list.vue',
 NULL, NULL, NULL, 'FileTextOutlined', NULL,
 0, NULL, 0, 0, 0,
 0, 1, NOW(), 1, NOW()),
(1003, '分段管理', 2, 1000, 3, '/business/dataset/segment', '/business/dataset/segment/segment-list.vue',
 NULL, NULL, NULL, 'BarsOutlined', NULL,
 0, NULL, 0, 0, 0,
 0, 1, NOW(), 1, NOW());

-- ---------------------------------------------------------------------
-- 3. 功能点（按钮权限）permission 串：kb:<实体>:<动作>
-- ---------------------------------------------------------------------
INSERT INTO `t_menu`
(`menu_id`, `menu_name`, `menu_type`, `parent_id`, `sort`, `path`, `component`,
 `perms_type`, `api_perms`, `web_perms`, `icon`, `context_menu_id`,
 `frame_flag`, `frame_url`, `cache_flag`, `visible_flag`, `disabled_flag`,
 `deleted_flag`, `create_user_id`, `create_time`, `update_user_id`, `update_time`)
VALUES
-- 知识库管理按钮
(1004, '知识库-查询', 3, 1001, 1, NULL, NULL, 1, 'kb:base:query', 'kb:base:query', NULL, 1001, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1005, '知识库-新增', 3, 1001, 2, NULL, NULL, 1, 'kb:base:add', 'kb:base:add', NULL, 1001, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1006, '知识库-编辑', 3, 1001, 3, NULL, NULL, 1, 'kb:base:update', 'kb:base:update', NULL, 1001, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1007, '知识库-删除', 3, 1001, 4, NULL, NULL, 1, 'kb:base:delete', 'kb:base:delete', NULL, 1001, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
-- 文档管理按钮
(1008, '文档-查询', 3, 1002, 1, NULL, NULL, 1, 'kb:doc:query', 'kb:doc:query', NULL, 1002, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1009, '文档-上传', 3, 1002, 2, NULL, NULL, 1, 'kb:doc:upload', 'kb:doc:upload', NULL, 1002, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1010, '文档-重命名', 3, 1002, 3, NULL, NULL, 1, 'kb:doc:rename', 'kb:doc:rename', NULL, 1002, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1011, '文档-删除', 3, 1002, 4, NULL, NULL, 1, 'kb:doc:delete', 'kb:doc:delete', NULL, 1002, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
(1012, '文档-解析', 3, 1002, 5, NULL, NULL, 1, 'kb:doc:parse', 'kb:doc:parse', NULL, 1002, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW()),
-- 分段管理按钮
(1013, '分段-查询', 3, 1003, 1, NULL, NULL, 1, 'kb:segment:query', 'kb:segment:query', NULL, 1003, 0, NULL, 0, 1, 0, 0, 1, NOW(), 1, NOW());

-- ---------------------------------------------------------------------
-- 4. 角色-菜单关联（超级管理员 role 1，管理员 role 2）
-- ---------------------------------------------------------------------
INSERT INTO `t_role_menu` (`role_id`, `menu_id`, `update_time`, `create_time`)
SELECT 1, menu_id, NOW(), NOW() FROM `t_menu`
WHERE menu_id BETWEEN 1000 AND 1013
  AND menu_id NOT IN (SELECT menu_id FROM `t_role_menu` WHERE role_id = 1);

INSERT INTO `t_role_menu` (`role_id`, `menu_id`, `update_time`, `create_time`)
SELECT 2, menu_id, NOW(), NOW() FROM `t_menu`
WHERE menu_id BETWEEN 1000 AND 1013
  AND menu_id NOT IN (SELECT menu_id FROM `t_role_menu` WHERE role_id = 2);
