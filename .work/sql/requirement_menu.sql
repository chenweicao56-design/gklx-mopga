-- =====================================================================
-- 需求管理模块 - 菜单 & 权限 修正脚本 (mopga-gen 库)
--
-- 背景:
--   数据库中已存在需求相关菜单 (t_menu 900-915)，但存在两处错误:
--     1) 三个页面菜单(901/902/903)的 component 路径指向不存在的视图文件
--     2) 按钮级菜单(904-915)的权限串为 reqProject:* / reqRepo:* / reqReq:*，
--        与后端 Controller 和前端 v-privilege 实际使用的 requirement:* 不一致
--   本脚本负责修正 component 路径、权限串，并补充“需求-状态流转”按钮与
--   超级管理员(role 1 / develop)的角色-菜单关联。
--
-- 执行方式: 在 mysql 客户端或任意 SQL 工具中对 mopga-gen 库执行本文件。
--   请先执行 requirement_schema.sql 建表，再执行本脚本。
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. 修正三个页面菜单的 path 与 component (指向真实视图文件)
-- ---------------------------------------------------------------------
UPDATE `t_menu`
SET `path` = '/business/requirement/project',
    `component` = '/business/requirement/project/project-list',
    `update_user_id` = 1,
    `update_time` = NOW()
WHERE `menu_id` = 901;

UPDATE `t_menu`
SET `path` = '/business/requirement/repo',
    `component` = '/business/requirement/repo/repo-list',
    `update_user_id` = 1,
    `update_time` = NOW()
WHERE `menu_id` = 902;

UPDATE `t_menu`
SET `path` = '/business/requirement/req',
    `component` = '/business/requirement/requirement/requirement-list',
    `update_user_id` = 1,
    `update_time` = NOW()
WHERE `menu_id` = 903;

-- ---------------------------------------------------------------------
-- 2. 修正按钮级菜单的权限串 (api_perms + web_perms)
-- ---------------------------------------------------------------------
-- 项目管理按钮
UPDATE `t_menu` SET `api_perms`='requirement:project:query', `web_perms`='requirement:project:query',  `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=904;
UPDATE `t_menu` SET `api_perms`='requirement:project:add',   `web_perms`='requirement:project:add',    `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=905;
UPDATE `t_menu` SET `api_perms`='requirement:project:update',`web_perms`='requirement:project:update', `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=906;
UPDATE `t_menu` SET `api_perms`='requirement:project:delete',`web_perms`='requirement:project:delete', `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=907;
-- 工程管理按钮
UPDATE `t_menu` SET `api_perms`='requirement:repo:query', `web_perms`='requirement:repo:query',  `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=908;
UPDATE `t_menu` SET `api_perms`='requirement:repo:add',   `web_perms`='requirement:repo:add',    `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=909;
UPDATE `t_menu` SET `api_perms`='requirement:repo:update',`web_perms`='requirement:repo:update', `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=910;
UPDATE `t_menu` SET `api_perms`='requirement:repo:delete',`web_perms`='requirement:repo:delete', `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=911;
-- 需求管理按钮
UPDATE `t_menu` SET `api_perms`='requirement:req:query', `web_perms`='requirement:req:query',  `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=912;
UPDATE `t_menu` SET `api_perms`='requirement:req:add',   `web_perms`='requirement:req:add',    `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=913;
UPDATE `t_menu` SET `api_perms`='requirement:req:update',`web_perms`='requirement:req:update', `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=914;
UPDATE `t_menu` SET `api_perms`='requirement:req:delete',`web_perms`='requirement:req:delete', `update_user_id`=1, `update_time`=NOW() WHERE `menu_id`=915;

-- ---------------------------------------------------------------------
-- 3. 新增“需求-状态流转”按钮菜单 (父菜单 903)
-- ---------------------------------------------------------------------
INSERT INTO `t_menu`
(`menu_id`, `menu_name`, `menu_type`, `parent_id`, `sort`, `path`, `component`,
 `perms_type`, `api_perms`, `web_perms`, `icon`, `context_menu_id`,
 `frame_flag`, `frame_url`, `cache_flag`, `visible_flag`, `disabled_flag`,
 `deleted_flag`, `create_user_id`, `create_time`, `update_user_id`, `update_time`)
VALUES
(916, '需求-状态流转', 3, 903, 5, NULL, NULL,
 1, 'requirement:req:status', 'requirement:req:status', NULL, NULL,
 0, NULL, 0, 1, 0,
 0, 1, NOW(), 1, NOW());

-- ---------------------------------------------------------------------
-- 4. 补充超级管理员(role 1 / develop) 与需求菜单的角色关联
--    (manager 角色 role 2 已包含 900-915，补 916 状态流转即可)
-- ---------------------------------------------------------------------
INSERT INTO `t_role_menu` (`role_id`, `menu_id`, `update_time`, `create_time`)
SELECT 1, menu_id, NOW(), NOW() FROM `t_menu`
WHERE menu_id BETWEEN 900 AND 916
  AND menu_id NOT IN (SELECT menu_id FROM `t_role_menu` WHERE role_id = 1);

INSERT INTO `t_role_menu` (`role_id`, `menu_id`, `update_time`, `create_time`)
SELECT 2, 916, NOW(), NOW() FROM DUAL
WHERE 916 NOT IN (SELECT menu_id FROM `t_role_menu` WHERE role_id = 2);

-- =====================================================================
-- 附: 核对语句 (可选)
--   SELECT menu_id,menu_name,menu_type,parent_id,path,component,api_perms,web_perms FROM t_menu WHERE menu_id BETWEEN 900 AND 916 ORDER BY menu_id;
--   SELECT role_id, COUNT(*) FROM t_role_menu WHERE menu_id BETWEEN 900 AND 916 GROUP BY role_id;
-- =====================================================================
