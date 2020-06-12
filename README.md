#### OpenSource_Startup 后端项目
技术栈包含 Spring Boot、 Jpa、 Spring Security、Mysql、Redis（添加中..）、
##### 一、数据库配置
  ###### 创建数据库
  - Mysql 5.0以上
  - 运行opensource_startup.sql
  ###### 配置数据源
  ####### 开发环境
  - 打开resources/config/application-dev.yml
  - 配置spring:datasource:druid:
  ####### 生产环境
  - 打开resources/config/application-prod.yml
  - 配置spring:datasource:druid:
  
##### 二、微信配置
  - 打开resources/config/application.yml
  - 配置wxMini:appId: secret
 
  