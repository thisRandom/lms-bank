# LMS 物流管理系统后端

物流管理系统（Logistics Management System）后端服务，基于 Spring Boot 构建。

## 前端项目

前端代码仓库：https://github.com/thisRandom/lms-front

## 技术栈

- Spring Boot 
- MyBatis
- MySQL 8.0
- Redis
- JWT 身份认证

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/thisRandom/lms-bank.git
cd lms-bank
```

### 2. 启动 Redis

确保 Redis 服务已启动，默认配置：
- 地址：localhost
- 端口：6379
- 密码：无

```bash
# Linux/Mac
redis-server

# Windows
# 启动 Redis 服务或运行 redis-server.exe
```

### 3. 导入数据库

使用 Navicat 等工具直接导入 `sql/lms.sql` 文件。

### 4. 修改配置

编辑 `lms-app/src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lms?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
  data:
    redis:
      host: localhost
      port: 6379
```

### 5. 启动项目

```bash
# 编译打包
mvn clean package -DskipTests

# 启动项目
java -jar lms-app/target/lms-app-1.0.0.jar
```

或使用 IDE 直接运行 `LmsApplication` 类。

项目启动后访问：http://localhost:8090

## 项目结构

```
lms-bank
├── lms-common      # 公共模块（工具类、常量等）
├── lms-auth        # 认证授权模块
├── lms-user        # 用户管理模块
├── lms-vehicle     # 车辆管理模块
├── lms-order       # 订单管理模块
├── lms-dispatch    # 调度管理模块
├── lms-locations   # 地点管理模块
├── lms-app         # 启动模块（Controller、启动类）
└── sql             # 数据库脚本
```
