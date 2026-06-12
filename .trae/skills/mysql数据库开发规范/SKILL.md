---
name: MySql数据库开发规范
description: 在生成文档、生成代码、调用mcp接口等，如果涉及到数据库操作，比如建表，必须要参考该规范。
---

# MySQL 建表规范

## 一、命名规范

### 1.1 数据库/表/字段命名
- **小写蛇形命名**: `user_info`, `order_detail`
- **禁用关键字**: 避免使用 MySQL 关键字（如 `order`, `user`, `index`）
- **简洁明确**: 名称应清晰表达含义，如 `create_time` 而非 `ct`
- **前缀**: 用前缀标识模块，如 `sys_`:系统模块
- **后缀**: 标识表的特殊类型，如 `_log`：日志表、`_rel`：关系表

### 1.2 索引命名
- **主键**: `pk_<table_name>`
- **唯一索引**: `uk_<table_name>_<column>` 
- **普通索引**: `idx_<table_name>_<column>`
- **组合索引**: `idx_<table_name>_<col1>_<col2>`

## 二、字段规范

### 2.1 数据类型选择
| 类型 | 说明 | 示例 | 注意事项 |
|------|------|------|----------|
| `BIGINT` | 主键、大整数 | `id BIGINT` | 推荐使用，避免溢出 |
| `INT` | 普通整数 | `status INT` | 适用于中小规模数据 |
| `DATETIME` | 时间戳（无时区） | `create_time DATETIME` | 存储范围更大（1001-9999年） |
| `VARCHAR(n)` | 字符串 | `name VARCHAR(50)` | **根据实际长度选择**，避免过大（如无特殊需求，不要默认使用 255） |
| `DECIMAL(p,d)` | 金额、精确小数 | `amount DECIMAL(10,2)` | p为精度，d为小数位数 |
| `TINYINT` | 布尔值/状态 | `is_deleted TINYINT` | 1字节，适合枚举值 |
| `TEXT` | 长文本 | `content TEXT` | 超过 255 字符建议使用 |
| `JSON` | JSON数据 | `extra JSON` | MySQL 5.7+ 支持，查询性能较好 |

### 2.2 必须字段
```sql
`id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
`create_user_id` BIGINT NOT NULL COMMENT '创建人',
`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
```
#### 注意：
  - 在一些关联表中，可以没有create_user_id、create_time、update_user_id、update_time审计字段，比如：用户角色关联表
  - 在一些确定不会修改的表中可以没有update_user_id、update_time 审计字段，比如：日志表

### 2.3 字段约束
- **NOT NULL**: 所有字段默认 NOT NULL
- **默认值**: 必须提供合理的默认值
- **注释**: 每个字段必须添加 COMMENT 注释

## 三、索引规范

### 3.1 主键
- 每个表必须有主键
- 推荐使用 BIGINT + AUTO_INCREMENT
- 主键字段命名为 `id`

### 3.2 索引设计
- **复合索引**: 遵循最左前缀原则
- **索引数量**: 单表索引不超过 5 个
- **字段顺序**: 区分度高的字段放前面

### 3.3 唯一索引
- 业务唯一性字段必须加唯一索引
- 如：手机号、邮箱、订单号

## 四、表结构规范

### 4.1 表引擎
```sql
ENGINE=InnoDB
```

### 4.2 字符集
```sql
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci
```

### 4.3 分区表
- 超过 1000 万数据考虑分区
- 按时间或 ID 范围分区

## 五、SQL 示例

```sql
CREATE TABLE `user_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `user_name` VARCHAR(50) NOT NULL COMMENT '用户名',
  `email` VARCHAR(100) NOT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 1-启用, 0-禁用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_info_phone` (`phone`),
  KEY `idx_user_info_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
```

## 六、注意事项

1. **避免 NULL**: 尽量使用 NOT NULL 和默认值
2. **金额使用 DECIMAL**: 避免浮点数精度问题
3. **大字段分离**: TEXT/BLOB 字段单独建表
4. **禁止外键**: 业务逻辑层保证数据一致性
5. **合理分表**: 单表数据超过 500 万考虑分表