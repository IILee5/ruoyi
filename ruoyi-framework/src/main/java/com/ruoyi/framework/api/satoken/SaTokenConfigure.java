package com.ruoyi.framework.api.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.strategy.SaStrategy;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.framework.api.satoken.at.StpUserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;


/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author iilee
 */
@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer
{
    /**
     * 无需验证签名的接口列表
     */
    private final static String URL_NO_CHECK = "url_no_check";
    /**
     * 无需登录的接口列表
     */
    private final static String URL_NO_LOGIN = "url_no_login";

    /**
     * 注册Sa-Token 的拦截器，拦截API下客户端请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        List<String> notCheckUrl = DictUtils.getDictCache(URL_NO_CHECK).stream().map(SysDictData::getDictValue).collect(Collectors.toList());
        List<String> notLoginUrl = DictUtils.getDictCache(URL_NO_LOGIN).stream().map(SysDictData::getDictValue).collect(Collectors.toList());
        SaInterceptor interceptor = new SaInterceptor(
                handler ->
                {
                    SaRouter.match("/api/**").notMatch(notCheckUrl).check(r -> StpUserUtil.checkSign());
                    SaRouter.match("/api/**").notMatch(notLoginUrl).check(r -> StpUtil.checkLogin());
                });
        // 注册注解拦截器
        registry.addInterceptor(interceptor).addPathPatterns("/api/**");
    }

    /**
     * 注册 [Sa-Token 全局过滤器]
     */
    @Bean
    public SaServletFilter getSaServletFilter()
    {
        return new SaServletFilter()

                // 指定 [拦截路由] 与 [放行路由]
                .addInclude("/api/**")
                // 认证函数: 每次请求执行
                // .setAuth(obj -> log.info("---------- sa全局认证 " + SaHolder.getRequest().getRequestPath()))
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> R.fail(e.getMessage()))
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(r -> SaHolder.getResponse()
                        // 服务器名称
                        .setServer("ruoyi-api")
                        // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                        .setHeader("X-Frame-Options", "SAMEORIGIN")
                        // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                        .setHeader("X-XSS-Protection", "1; mode=block")
                        // 禁用浏览器内容嗅探
                        .setHeader("X-Content-Type-Options", "nosniff"));
    }

    /**
     * 重写 Sa-Token 框架内部算法策略
     */
    @Autowired
    public void rewriteSaStrategy()
    {
        // 重写Sa-Token的注解处理器，增加注解合并功能
        SaStrategy.me.getAnnotation = AnnotatedElementUtils::getMergedAnnotation;
    }

}
