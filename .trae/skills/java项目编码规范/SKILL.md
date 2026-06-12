---
name: java项目编码指南
description: 在生成代码/生成开发计划之前必须参考该指南
---

# 一、JAVA项目规范
## 1.1、Java 项目命名规范
全部采用小写方式， 以中划线分隔
## 1.2、方法参数规范
无论是 controller，service，manager，dao 亦或是其他 class 的代码，每个方法最多 5 个参数，如果超出 5 个参数的话，要封装成 javabean 对象。
## 1.3、module 目录规范
例如：系统module下用的用户功能模块，目录结构格式如下：
```text
src
|-- module                                所有业务模块
|-- |-- system                            系统模块
|-- |-- |-- const                         常量目录
|-- |-- |-- |-- UserConst.java            用户相关的常量
|-- |-- |-- controller                    controller目录
|-- |-- |-- |-- UserController.java       用户controller
|-- |-- |-- service                       service目录
|-- |-- |-- |-- UserService.java          用户service
|-- |-- |-- domain                        domain目录
|-- |-- |-- |-- entity                    entity目录
|-- |-- |-- |-- |-- UserEntity.java       用户表对应实体
|-- |-- |-- |-- form                      form目录
|-- |-- |-- |-- |-- UserQueryForm.java    用户查询表单
|-- |-- |-- |-- |-- UserAddForm.java      用户新增表单
|-- |-- |-- |-- |-- UserUpdateForm.java   用户更新表单
|-- |-- |-- |-- vo                        vo目录(返回对象)
|-- |-- |-- |-- |-- UserVo.java           用户返回对象
|-- |-- |-- dao                           dao目录
|-- |-- |-- |-- UserDao.java              用户dao
resources
|-- mapper                            mapper目录
|-- |-- system                        系统模块
|-- |-- |-- UserMapper.xml            用户mapper文件
```
