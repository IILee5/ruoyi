package com.ruoyi.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 程序注解配置
 * 注解 @EnableAspectJAutoProxy(exposeProxy = true) 表示通过aop框架暴露该代理对象,AopContext能够访问
 * 注解 @MapperScan("com.ruoyi.**.mapper") 指定要扫描的Mapper类的包的路径
 * 注解 @EnableAsync 表示开启@Async注解
 *
 * @author ruoyi
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
@MapperScan("com.ruoyi.**.mapper")
public class ApplicationConfig
{

}
