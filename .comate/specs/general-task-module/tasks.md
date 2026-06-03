# 通用任务模块任务清单

## 1. 实体类与枚举

- [x] 1.1 创建 TaskEntity 任务实体类
- [x] 1.2 创建 TaskLogEntity 任务日志实体类
- [x] 1.3 创建 TaskStatusEnum 任务状态枚举
- [x] 1.4 创建 TriggerModeEnum 触发模式枚举
- [x] 1.5 创建 HandlerTypeEnum 处理器类型枚举
- [x] 1.6 创建 ScenarioEnum 场景枚举

## 2. 数据访问层（DAO）

- [x] 2.1 创建 TaskDao 接口（继承 BaseMapper）
- [x] 2.2 创建 TaskLogDao 接口
- [x] 2.3 创建 MyBatis Mapper XML 文件

## 3. 表单与视图对象（Form/VO/DTO）

- [x] 3.1 创建 TaskForm 任务表单
- [x] 3.2 创建 TaskQueryForm 查询表单
- [x] 3.3 创建 TaskVO 任务视图对象
- [x] 3.4 创建 TaskLogVO 日志视图对象
- [x] 3.5 创建 PageTaskVO 分页响应对象

## 4. 服务层（Service）

- [x] 4.1 创建 TaskService 服务接口
- [x] 4.2 创建 TaskServiceImpl 服务实现类
- [x] 4.3 实现任务 CRUD 基础方法
- [x] 4.4 实现任务分页查询方法
- [x] 4.5 实现手动执行任务方法
- [x] 4.6 实现启用/禁用任务方法

## 5. 任务处理器（Handler）

- [x] 5.1 创建 TaskHandler 接口
- [x] 5.2 创建 AbstractTaskHandler 抽象基类
- [x] 5.3 创建 HttpTaskHandler HTTP调用处理器
- [x] 5.4 创建 TaskHandlerFactory 处理器工厂

## 6. 定时调度器（Scheduler）

- [x] 6.1 创建 TaskScheduler 定时调度器
- [x] 6.2 配置定时扫描任务逻辑
- [x] 6.3 实现任务执行状态更新

## 7. 控制器（Controller）

- [x] 7.1 创建 AdminTaskController 控制器
- [x] 7.2 实现创建任务接口
- [x] 7.3 实现更新任务接口
- [x] 7.4 实现删除任务接口
- [x] 7.5 实现查询任务列表接口
- [x] 7.6 实现查询任务详情接口
- [x] 7.7 实现手动执行任务接口
- [x] 7.8 实现启用/禁用任务接口

## 8. 配置与常量

- [x] 8.1 创建 TaskConstant 常量类
- [x] 8.2 创建 TaskConfig 配置类
- [x] 8.3 配置 YAML 配置文件

## 9. SQL 建表语句

- [x] 9.1 生成 t_task 建表 SQL
- [x] 9.2 生成 t_task_log 建表 SQL