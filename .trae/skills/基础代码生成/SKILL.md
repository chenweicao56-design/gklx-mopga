---
name: base-code-generator
description: 根据数据库表结构自动生成完整的基础代码（包括Entity、DAO、Service、Controller、Mapper等），支持建表、字段配置、代码生成与同步。当需要从数据库表生成后端基础代码时使用此技能。
---

# 基础代码生成器技能

## 技能概述

本技能通过 `gen-mcp-server` 服务，实现从数据库表设计到完整基础代码生成的自动化流程。无需手动编写重复的 CRUD 代码，大幅提升开发效率。

### 核心能力
- 🔨 自动建表：执行标准 SQL 创建数据库表
- ⚙️ 灵活配置：配置字段显示名、查询方式、表单类型等
- 📦 代码生成：一键生成 Entity、DAO、Service、Controller 等完整代码
- 🔄 增量同步：生成的代码自动同步到项目指定目录
- ✅ 质量检查：确保生成的代码符合项目规范

## 可用的 MCP 方法

| 方法 | 说明 | 必填参数 |
|------|------|----------|
| `executeTableSql` | 执行建表 SQL，在数据库中创建表 |
| `getTableInfo` | 获取表的字段配置信息 |
| `updateTable` | 更新表的配置信息（显示名、查询方式等） | 
| `syncTableAndGenerateCode` | 根据表配置生成代码并同步到项目目录 |

## 标准工作流程

### 流程图

```mermaid
graph TD
    A[开始] --> B[准备建表SQL]
    B --> C[executeTableSql 建表]
    C --> D[getTableInfo 获取配置]
    D --> E[updateTable 优化配置]
    E --> F[syncTableAndGenerateCode 生成代码]
    F --> G[代码质量检查]
    G --> H{代码合格？}
    H -->|是| I[结束]
    H -->|否| D