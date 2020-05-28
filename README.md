# Infrastructure-tools
项目基础框架+各种工具类+过滤器+拦截器

基础架构：
springboot+mybatis+tkmybatis+mysql+redis+memcached

工具类：   
AESUtil  
DateUtils  
HttpUtils  
Md5Utils  
RedisUtil  
RequestUtil  
StringUtils  
SftpUtil 

配置：
MemcacheConfiguration  
RedisConfig

过滤器：
过滤不合法请求url

拦截器：
根据项目环境和注解，对指定包下的http请求的响应AES加密

mybatis自动生成 entity实体、mapper、xml 工具类：
MybatisGeneratorUtil generatorConfig.xml

swagger配置，生成接口文档：
SwaggerConfig

日志生成配置，按日志类型、时间 生成日志：
logback-spring.xml
