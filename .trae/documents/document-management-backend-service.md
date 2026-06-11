# 文档管理系统 - 后端服务开发计划

## 1. 概述

本文档描述文档管理系统的后端服务开发计划，包括数据库表创建、Java 实体类、DAO 层、Service 层和 Controller 层的实现。

### 1.1 开发目标

实现文档管理系统的后端 API 服务，支持以下功能：
- 文档的增删改查
- 文档版本管理
- 标签管理
- 权限控制（只有创建人可操作）
- 查询过滤（按标签、文件名称、文档类型、文件类型）

### 1.2 技术栈

- Java 17
- Spring Boot 3.5.4
- MyBatis-Plus 3.5.12
- Sa-Token 1.44.0（权限认证）
- MySQL 8.0
- Lombok

## 2. 当前状态分析

### 2.1 项目现有结构

项目采用模块化架构：
- `mopga-admin`：管理后台模块
- `mopga-base`：基础模块

### 2.2 现有相关功能

- **字典管理**：`support/dict` 模块，用于维护预设标签、文件类型、文档类型等字典数据
- **数据追踪**：`support/datatracer` 模块，用于记录数据变更历史（但不用于文档版本）

### 2.3 需要新增的功能模块

需要创建 `document` 模块，包含以下子模块：
- `document`：文档管理主模块
- `tag`：标签管理模块

## 3. 后端实现计划

### 阶段一：数据库表创建

#### 1. 创建文档表 `t_document`

```sql
CREATE TABLE `t_document` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `document_name` VARCHAR(100) NOT NULL COMMENT '文档名称',
  `document_content` TEXT COMMENT '文档内容',
  `file_type_dict_data_id` BIGINT DEFAULT NULL COMMENT '文件类型（字典数据ID）',
  `document_type_dict_data_id` BIGINT DEFAULT NULL COMMENT '文档类型（字典数据ID）',
  `version` VARCHAR(20) DEFAULT NULL COMMENT '当前版本号',
  PRIMARY KEY (`id`),
  KEY `idx_t_document_name` (`document_name`),
  KEY `idx_t_document_file_type` (`file_type_dict_data_id`),
  KEY `idx_t_document_doc_type` (`document_type_dict_data_id`),
  KEY `idx_t_document_create_user` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档表';
```

#### 2. 创建文档版本表 `t_document_version`

```sql
CREATE TABLE `t_document_version` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `document_id` BIGINT NOT NULL COMMENT '文档ID',
  `version` VARCHAR(20) NOT NULL COMMENT '版本号',
  `document_content` TEXT COMMENT '文档内容快照',
  PRIMARY KEY (`id`),
  KEY `idx_t_document_version_document` (`document_id`, `version`),
  KEY `idx_t_document_version_create_user` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档版本表';
```

#### 3. 创建标签表 `t_tag`

```sql
CREATE TABLE `t_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `tag_name` VARCHAR(50) NOT NULL COMMENT '标签名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_t_tag_name` (`tag_name`),
  KEY `idx_t_tag_create_user` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';
```

#### 4. 创建文档标签关联表 `t_document_tag`

```sql
CREATE TABLE `t_document_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `create_user_id` BIGINT NOT NULL COMMENT '创建人',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_user_id` BIGINT DEFAULT NULL COMMENT '更新人',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '删除状态',
  `document_id` BIGINT NOT NULL COMMENT '文档ID',
  `tag_id` BIGINT NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  KEY `idx_t_document_tag_document` (`document_id`, `tag_id`),
  KEY `idx_t_document_tag_tag` (`tag_id`, `document_id`),
  KEY `idx_t_document_tag_create_user` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文档标签关联表';
```

#### 5. 添加字典数据

需要在 `t_dict` 表中添加字典定义：

**文件类型字典：**
- dict_code: `file_type`
- dict_name: `文件类型`

字典数据：
- data_value: `markdown`, data_label: `Markdown`
- data_value: `json`, data_label: `JSON`
- data_value: `text`, data_label: `文本`
- data_value: `code`, data_label: `代码`
- data_value: `html`, data_label: `HTML`
- data_value: `xml`, data_label: `XML`
- data_value: `sql`, data_label: `SQL`
- data_value: `yaml`, data_label: `YAML`
- data_value: `csv`, data_label: `CSV`

**文档类型字典：**
- dict_code: `document_type`
- dict_name: `文档类型`

字典数据：
- data_value: `tech_doc`, data_label: `技术文档`
- data_value: `user_manual`, data_label: `用户手册`
- data_value: `config`, data_label: `配置文件`
- data_value: `api_doc`, data_label: `API文档`
- data_value: `meeting`, data_label: `会议记录`
- data_value: `other`, data_label: `其他`

---

### 阶段二：实体类和 DAO 层

#### 1. 文档实体类

**文件路径**：`mopga-admin/src/main/java/com/gklx/mopga/admin/module/business/document/domain/entity/DocumentEntity.java`

```java
@Data
@TableName("t_document")
public class DocumentEntity {
    @TableId(type = IdType.AUTO)
    private Long documentId;
    
    private Long createUserId;
    private LocalDateTime createTime;
    private Long updateUserId;
    private LocalDateTime updateTime;
    private Boolean deletedFlag;
    
    private String documentName;
    private String documentContent;
    private Long fileTypeDictDataId;
    private Long documentTypeDictDataId;
    private String version;
}
```

#### 2. 文档版本实体类

**文件路径**：`mopga-admin/src/main/java/com/gklx/mopga/admin/module/business/document/domain/entity/DocumentVersionEntity.java`

```java
@Data
@TableName("t_document_version")
public class DocumentVersionEntity {
    @TableId(type = IdType.AUTO)
    private Long versionId;
    
    private Long documentId;
    private String version;
    private String documentContent;
    
    private Long createUserId;
    private LocalDateTime createTime;
    private Long updateUserId;
    private LocalDateTime updateTime;
    private Boolean deletedFlag;
}
```

#### 3. 标签实体类

**文件路径**：`mopga-admin/src/main/java/com/gklx/mopga/admin/module/business/tag/domain/entity/TagEntity.java`

```java
@Data
@TableName("t_tag")
public class TagEntity {
    @TableId(type = IdType.AUTO)
    private Long tagId;
    
    private String tagName;
    
    private Long createUserId;
    private LocalDateTime createTime;
    private Long updateUserId;
    private LocalDateTime updateTime;
    private Boolean deletedFlag;
}
```

#### 4. 文档标签关联实体类

**文件路径**：`mopga-admin/src/main/java/com/gklx/mopga/admin/module/business/document/domain/entity/DocumentTagEntity.java`

```java
@Data
@TableName("t_document_tag")
public class DocumentTagEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long documentId;
    private Long tagId;
    
    private Long createUserId;
    private LocalDateTime createTime;
    private Long updateUserId;
    private LocalDateTime updateTime;
    private Boolean deletedFlag;
}
```

#### 5. DAO 接口

**DocumentDao**：
- `insert`：插入文档（MyBatis-Plus）
- `updateById`：更新文档（MyBatis-Plus）
- `selectById`：查询文档（MyBatis-Plus）
- `queryPage`：分页查询（自定义）
- `selectByCreateUserId`：按创建人查询
- `batchDeleteByIds`：批量删除（MyBatis-Plus）

**DocumentVersionDao**：
- `insert`：插入版本（MyBatis-Plus）
- `selectByDocumentId`：按文档ID查询版本列表
- `selectByVersion`：按版本号查询
- `deleteByVersion`：删除指定版本

**TagDao**：
- `insert`：插入标签（MyBatis-Plus）
- `updateById`：更新标签（MyBatis-Plus）
- `selectById`：查询标签（MyBatis-Plus）
- `selectAll`：查询所有标签
- `queryPage`：分页查询（自定义）
- `selectByName`：按标签名查询

**DocumentTagDao**：
- `insert`：插入关联（MyBatis-Plus）
- `deleteByDocumentId`：按文档ID删除关联
- `selectByDocumentId`：按文档ID查询标签ID列表
- `selectByTagId`：按标签ID查询文档ID列表

---

### 阶段三：VO 和 Form 类

#### 1. 文档 VO 类

**DocumentVO** - 查询结果对象
- documentId
- documentName
- documentContent
- fileTypeDictDataId
- documentTypeDictDataId
- version
- createUserId
- createTime
- updateUserId
- updateTime
- deletedFlag

**DocumentDetailVO** - 详情对象
- 包含 DocumentVO 所有字段
- tagNameList：标签列表（String）

#### 2. 文档 Form 类

**DocumentAddForm** - 新增表单
- documentName（必填）
- documentContent（必填）
- fileTypeDictDataId（可选）
- documentTypeDictDataId（可选）
- version（必填）
- tagNameList（可选）

**DocumentUpdateForm** - 更新表单
- documentId（必填）
- documentName（必填）
- documentContent（必填）
- fileTypeDictDataId（可选）
- documentTypeDictDataId（可选）
- version（必填）
- tagNameList（可选）

#### 3. 版本 VO 类

**DocumentVersionVO**
- versionId
- documentId
- version
- documentContent
- createUserId
- createTime
- updateUserId
- updateTime
- deletedFlag

#### 4. 标签 VO 类

**TagVO**
- tagId
- tagName
- createUserId
- createTime
- updateUserId
- updateTime
- deletedFlag

---

### 阶段四：Service 层

#### 1. DocumentService

**方法列表**：
- `queryPage(DocumentQueryForm)` - 分页查询
- `getDetail(Long documentId)` - 获取详情
- `add(DocumentAddForm)` - 新增文档
- `update(DocumentUpdateForm)` - 更新文档
- `delete(Long documentId)` - 删除文档
- `batchDelete(List<Long>)` - 批量删除
- `addVersion(Long documentId, String version)` - 添加版本
- `queryVersionList(Long documentId)` - 查询历史版本
- `deleteVersion(Long versionId)` - 删除历史版本
- `queryByTagName(String tagName)` - 按标签查询

**业务规则**：
- 只有创建人可以编辑/删除
- 新增时记录审计字段
- 更新时需要检查权限
- 删除文档时同步删除所有历史版本
- 版本创建时内容快照到版本表

#### 2. TagService

**方法列表**：
- `queryPage(TagQueryForm)` - 分页查询标签
- `getAll()` - 获取所有标签
- `add(TagAddForm)` - 添加标签
- `update(TagUpdateForm)` - 更新标签
- `delete(Long tagId)` - 删除标签
- `batchDelete(List<Long>)` - 批量删除标签

**业务规则**：
- 标签名唯一
- 预设标签不能删除（通过字典维护的）

#### 3. DocumentTagService

**方法列表**：
- `addTags(Long documentId, List<Long> tagIdList)` - 添加标签关联
- `updateTags(Long documentId, List<Long> tagIdList)` - 更新标签关联
- `deleteByDocumentId(Long documentId)` - 删除文档的标签关联
- `getTagIdsByDocumentId(Long documentId)` - 获取文档的标签ID列表

---

### 阶段五：Controller 层

#### 1. DocumentController

**API 端点**：
- `POST /document/queryPage` - 分页查询文档
- `GET /document/getDetail/{documentId}` - 获取文档详情
- `POST /document/add` - 新增文档
- `POST /document/update` - 更新文档
- `POST /document/delete/{documentId}` - 删除文档
- `POST /document/batchDelete` - 批量删除文档
- `POST /document/addVersion/{documentId}/{version}` - 添加版本
- `GET /document/queryVersionList/{documentId}` - 查询历史版本
- `DELETE /document/deleteVersion/{versionId}` - 删除历史版本
- `POST /document/queryByTagName` - 按标签查询文档

**权限控制**：
- 查询：`document:query`
- 新增：`document:add`
- 更新：`document:update`
- 删除：`document:delete`
- 版本管理：`document:version`

#### 2. TagController

**API 端点**：
- `POST /tag/queryPage` - 分页查询标签
- `GET /tag/getAll` - 获取所有标签
- `POST /tag/add` - 添加标签
- `POST /tag/update` - 更新标签
- `POST /tag/delete/{tagId}` - 删除标签
- `POST /tag/batchDelete` - 批量删除标签

**权限控制**：
- 查询：`tag:query`
- 新增：`tag:add`
- 更新：`tag:update`
- 删除：`tag:delete`

---

### 阶段六：Mapper XML

#### 1. DocumentMapper.xml

**SQL 查询**：
- `queryPage`：分页查询，支持按文件名称、文件类型、文档类型过滤
- `queryByTagName`：按标签名模糊查询文档
- `insert`：插入文档
- `updateById`：更新文档

#### 2. DocumentVersionMapper.xml

**SQL 查询**：
- `queryVersionList`：查询文档的历史版本
- `insert`：插入版本快照
- `deleteByVersion`：删除指定版本

#### 3. TagMapper.xml

**SQL 查询**：
- `queryPage`：分页查询标签
- `selectAll`：查询所有标签
- `insert`：插入标签
- `updateById`：更新标签

#### 4. DocumentTagMapper.xml

**SQL 查询**：
- `addTags`：批量插入标签关联
- `deleteByDocumentId`：删除文档的所有标签关联
- `selectByDocumentId`：查询文档的标签ID列表

---

## 4. 实现步骤

### Step 1：数据库表创建
- 创建 `t_document` 表
- 创建 `t_document_version` 表
- 创建 `t_tag` 表
- 创建 `t_document_tag` 表
- 添加字典数据（文件类型、文档类型、预设标签）

### Step 2：实体类和 DAO 层
- 创建 DocumentEntity、DocumentVersionEntity、TagEntity、DocumentTagEntity
- 创建 DocumentDao、DocumentVersionDao、TagDao、DocumentTagDao
- 创建对应的 Mapper XML

### Step 3：VO 和 Form 类
- 创建 DocumentVO、DocumentDetailVO、DocumentAddForm、DocumentUpdateForm
- 创建 DocumentVersionVO
- 创建 TagVO、TagAddForm、TagUpdateForm

### Step 4：Service 层
- 实现 DocumentService
- 实现 TagService
- 实现 DocumentTagService

### Step 5：Controller 层
- 实现 DocumentController
- 实现 TagController

### Step 6：单元测试
- 测试基本的 CRUD 操作
- 测试权限控制
- 测试版本管理
- 测试标签关联

---

## 5. 文件清单

### 需要创建的文件

**实体类（4个）**：
1. `DocumentEntity.java`
2. `DocumentVersionEntity.java`
3. `TagEntity.java`
4. `DocumentTagEntity.java`

**VO 类（3个）**：
1. `DocumentVO.java`
2. `DocumentDetailVO.java`
3. `DocumentVersionVO.java`
4. `TagVO.java`

**Form 类（4个）**：
1. `DocumentAddForm.java`
2. `DocumentQueryForm.java`
3. `DocumentUpdateForm.java`
4. `TagAddForm.java`
5. `TagQueryForm.java`
6. `TagUpdateForm.java`

**DAO 接口（4个）**：
1. `DocumentDao.java`
2. `DocumentVersionDao.java`
3. `TagDao.java`
4. `DocumentTagDao.java`

**Mapper XML（4个）**：
1. `DocumentMapper.xml`
2. `DocumentVersionMapper.xml`
3. `TagMapper.xml`
4. `DocumentTagMapper.xml`

**Service 类（3个）**：
1. `DocumentService.java`
2. `TagService.java`
3. `DocumentTagService.java`

**Controller 类（2个）**：
1. `DocumentController.java`
2. `TagController.java`

---

## 6. 预期成果

完成本计划后，将实现以下功能：

1. ✅ 文档 CRUD 操作
2. ✅ 文档版本管理（查看历史版本、添加版本、删除历史版本）
3. ✅ 标签管理（增删改查、关联文档）
4. ✅ 权限控制（只有创建人可操作）
5. ✅ 查询过滤（按标签、文件名称、文档类型、文件类型）
6. ✅ 审计字段自动填充
7. ✅ 数据字典维护（文件类型、文档类型、预设标签）
