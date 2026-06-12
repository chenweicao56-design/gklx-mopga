---
name: base-code-generator
description: 根据 SQL 脚本创建数据库表，并根据表结构自动生成基础代码的 MCP Server 使用说明
---

- 参考**MySql数据库开发规范**skills 规范生成数据库建表语句
- 调用**gen-mcp-server** 的executeTableSql方法生成数据库表
- 调用**gen-mcp-server** 的syncTableAndGenerateCode生成基础代码
- 分析生成的基础代码，是否符合规范，并动态优化


