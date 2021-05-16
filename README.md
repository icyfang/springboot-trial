# springboot-trial

Log the experience of studying Spring Boot.

## Contents

### Spring Cache

使用 redis 和 ConcurrentMap 作为缓存容器。

集成功能：

- [ ] 缓存加载、刷新、删除
- [ ] 注解参数中使用 SpEL
- [ ] 使用 redis 时，为 key 设置不同有效时间
- [ ] keyGenerator

### Spring MVC

集成功能：
- [x] @ControllerAdvice 实现全局异常处理和返回体包装
- [x] AOP 异常处理
- [x] 通过 @Component、@WebFilter、ServletContextInitializer 三种方式配置 Filter
- [x] jsr303
- [x] Swagger 
- [ ] AOP 记录请求参数

### Spring JPA

集成功能：

- [x] 实体类的继承
- [x] 多表关联
    - [x] 多对多
    - [x] 一对多
    - [x] 一对一
- [x] JpaRepository 方法名解析
- [x] 使用 @NamedQuery、@Query 编写 SQL
    - [] 条件判断 
- [x] 审计功能，记录 createTime、createUser 和 updateTime、updateUser
- [x] 多数据源
- [ ] @Transactional 传播属性
- [x] @Version 实现乐观锁
- [ ] Criteria 进行复杂条件查询
### Spring Security

#### Spring Security OAuth2
### Spring Redis

使用 Redisson、Lettuce、RedisTemplate。集成功能：

- [x] RedisTemplate 进行增删改查
- [x] Redisson 实现 BloomFilter
- [ ] 集群 

### Spring Context

集成功能：

- [x] @Conditional 实现不同环境下 Bean 配置
- [x] 容器生命周期
- [x] 属性加载
- [x] 使用 ImportSelector、ImportBeanDefinitionRegistrar 配置 Bean
- [x] @Aync 实现异步任务
- [x] @Scheduled 实现定时任务
### Swagger
### Mybatis Plus

包括 Mybatis 和 Mybatis Plus。集成功能：

- [x] 扩展 `BaseMapper#insert`，实现 batch insert
- [x] LocalDate 和 LocalDateTime 的 TypeHandler
- [ ] association


### RabbitMQ
