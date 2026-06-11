package com.gklx.mopga.admin;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.UUID;
import com.gklx.mopga.admin.ai.mcp.GenerateMcp;
import com.gklx.mopga.admin.module.system.login.domain.RequestEmployee;
import com.gklx.mopga.admin.module.system.login.manager.LoginManager;
import com.gklx.mopga.base.common.constant.StringConst;
import com.gklx.mopga.base.common.util.SmartRequestUtil;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class McpTest {

    @Resource
    GenerateMcp generateMcp;
    @Resource
    LoginManager loginManager;


    private static final String SUPER_PASSWORD_LOGIN_ID_PREFIX = "S";



    @Test
    void contextLoads() {
        String createTableSql = """
                CREATE TABLE `user_info` (
                  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
                  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
                  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
                  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                  `deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态: 0-未删除, 1-已删除',
                  `user_name` VARCHAR(50) NOT NULL COMMENT '用户名',
                  `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
                  `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
                  `password` VARCHAR(100) NOT NULL COMMENT '密码',
                  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
                  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
                  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
                  `gender` TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
                  `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
                  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
                  `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
                  PRIMARY KEY (`id`),
                  UNIQUE KEY `uk_user_info_phone` (`phone`),
                  UNIQUE KEY `uk_user_info_email` (`email`),
                  KEY `idx_user_info_status` (`status`),
                  KEY `idx_user_info_user_name` (`user_name`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
                """;

        RequestEmployee requestEmployee = loginManager.getRequestEmployee(1L);
        SmartRequestUtil.setRequestUser(requestEmployee);
        generateMcp.generateCodeByCreateTableSql(2L, "user_info", createTableSql);
    }
}
