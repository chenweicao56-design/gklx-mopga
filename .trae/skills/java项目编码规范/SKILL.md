---
name: java项目编码指南
description: 在生成代码之前必须参考该指南
---

# 推荐流程
1. 调用gen-mcp-server（mcp服务）中的 executeTableSql 方法 在数据库中创建表
2. 调用gen-mcp-server（mcp服务）中的 syncTableAndGenerateCode 方法 生成基础代码
3. 基于生成的基础代码进行业务逻辑开发

