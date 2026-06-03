# 通用任务模块规格说明

## 1. 需求概述

创建一个通用任务模块，支持：
- 任务的增删改查（CRUD）
- 任务区分不同场景（scenario）
- 定时扫描任务，根据任务状态执行任务

## 2. 技术方案

### 2.1 技术栈
- **持久层**：MyBatis-Plus（与项目现有技术一致）
- **调度**：Spring `@Scheduled` + `SmartJob`（项目已有框架）
- **分层**：Controller → Service → Dao → Entity

### 2.2 模块位置
```
mopga-admin/src/main/java/com/gklx/mopga/admin/module/task/
├── constant/         # 常量定义
├── controller/       # 控制器
├── dao/              # 数据访问层
├── domain/
│   ├── dto/          # 数据传输对象
│   ├── entity/       # 实体类
│   ├── form/         # 表单对象
│   └── vo/           # 视图对象
├── enums/            # 枚举类
├── handler/          # 任务执行处理器
├── scheduler/        # 定时调度器
└── service/          # 服务层接口及实现
```

## 3. 数据模型

### 3.1 任务表 `t_task`

| 字段名 | 类型 | 注释 | 说明 |
|--------|------|------|------|
| task_id | bigint | 主键 | 自增 |
| task_code | varchar(50) | 任务编码 | 唯一标识 |
| task_name | varchar(100) | 任务名称 | |
| scenario | varchar(50) | 场景 | 用于区分不同场景，如：数据同步、报表生成、清理任务等 |
| handler_type | varchar(50) | 处理器类型 | 如：http、script、sql等 |
| handler_config | text | 处理器配置 | JSON格式，存储具体执行参数 |
| status | tinyint | 状态 | 1:待执行 2:执行中 3:已完成 4:失败 |
| cron_expression | varchar(50) | CRON表达式 | 定时执行的时间表达式，为空则不自动执行 |
| enabled_flag | tinyint | 启用标志 | 1:启用 0:禁用 |
| trigger_mode | tinyint | 触发模式 | 1:手动 2:定时 3:条件触发 |
| max_retry | int | 最大重试次数 | 默认3次 |
| retry_count | int | 当前重试次数 | |
| last_execute_time | datetime | 最后执行时间 | |
| next_execute_time | datetime | 下次执行时间 | 根据CRON计算 |
| execute_result | text | 执行结果 | 记录成功/失败信息 |
| remark | varchar(500) | 备注 | |
| sort | int | 排序 | |
| deleted_flag | tinyint | 删除标志 | 1:已删除 |
| create_time | datetime | 创建时间 | |
| update_time | datetime | 更新时间 | |

### 3.2 任务执行日志表 `t_task_log`

| 字段名 | 类型 | 注释 |
|--------|------|------|
| log_id | bigint | 主键 |
| task_id | bigint | 任务ID |
| task_name | varchar(100) | 任务名称 |
| trigger_type | tinyint | 触发类型 1:手动 2:定时 |
| start_time | datetime | 开始时间 |
| end_time | datetime | 结束时间 |
| execute_millis | bigint | 执行耗时(毫秒) |
| status | tinyint | 状态 1:成功 2:失败 |
| execute_result | text | 执行结果详情 |
| create_time | datetime | 创建时间 |

## 4. 功能模块

### 4.1 任务管理（CRUD）

#### 4.1.1 创建任务
- **API**: `POST /admin/task/create`
- **参数**: TaskForm（taskName, scenario, handlerType, handlerConfig, cronExpression, triggerMode, maxRetry, remark, sort）
- **返回**: 任务ID

#### 4.1.2 更新任务
- **API**: `PUT /admin/task/update`
- **参数**: TaskForm + taskId
- **返回**: 成功/失败

#### 4.1.3 删除任务
- **API**: `DELETE /admin/task/delete/{taskId}`
- **返回**: 成功/失败（软删除）

#### 4.1.4 查询任务列表
- **API**: `GET /admin/task/list`
- **参数**: scenario(可选), status(可选), pageNum, pageSize
- **返回**: 分页结果

#### 4.1.5 查询任务详情
- **API**: `GET /admin/task/{taskId}`
- **返回**: TaskVO

#### 4.1.6 手动执行任务
- **API**: `POST /admin/task/execute/{taskId}`
- **返回**: 执行结果

#### 4.1.7 启用/禁用任务
- **API**: `POST /admin/task/enable/{taskId}`
- **参数**: enabledFlag
- **返回**: 成功/失败

### 4.2 任务场景

场景（scenario）用于分类管理任务：
- `data_sync` - 数据同步
- `report` - 报表生成
- `cleanup` - 清理任务
- `backup` - 数据备份
- `notification` - 通知任务
- `custom` - 自定义场景

### 4.3 定时扫描与执行

#### 4.3.1 调度器职责
- 每分钟扫描一次数据库
- 筛选已启用且有CRON表达式的任务
- 根据CRON计算下次执行时间
- 时间到达时触发任务执行
- 更新任务状态和执行结果

#### 4.3.2 任务状态流转
```
待执行(1) -> 执行中(2) -> 已完成(3)
                   └-> 失败(4) -> 待重试 -> 执行中(2)
```

#### 4.3.3 执行流程
1. 获取任务，校验状态
2. 更新状态为"执行中"
3. 创建执行日志
4. 根据handlerType调用对应处理器
5. 更新执行结果
6. 更新任务状态（成功/失败）
7. 记录执行日志

## 5. 任务处理器

### 5.1 处理器接口
```java
public interface TaskHandler {
    /**
     * 执行任务
     * @param config 处理器配置（JSON）
     * @return 执行结果
     */
    String execute(String config);
}
```

### 5.2 内置处理器
- **HttpTaskHandler**: HTTP调用处理器
- **ScriptTaskHandler**: 脚本执行处理器
- **SqlTaskHandler**: SQL执行处理器

## 6. 异常处理

- 执行超时不阻塞后续调度
- 失败重试达到最大次数后停止
- 异常信息记录到执行日志
- 任务状态记录最终错误信息

## 7. 配置文件

在 `mopga-admin/src/main/resources/dev/sa-admin.yaml` 添加配置：
```yaml
task:
  scheduler:
    enabled: true
    scan-interval: 60000  # 扫描间隔（毫秒），默认60秒
    max-retry: 3          # 默认最大重试次数
```