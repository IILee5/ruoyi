package com.ruoyi.framework.web.exception;

import javax.servlet.http.HttpServletRequest;

import cn.dev33.satoken.exception.SaTokenException;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.ApiException;
import com.ruoyi.common.exception.NoCheckException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.DemoModeException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.security.PermissionUtils;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截API签名失败异常
     */
    @ExceptionHandler(NoCheckException.class)
    public R<String> noCheckException(NoCheckException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.warn("请求地址 : 【{}】，API用户签名失败 : {}", requestURI, e.getMessage());
        return R.noCheck(e.getMessage());
    }

    /**
     * 拦截API未登录异常
     */
    @ExceptionHandler(SaTokenException.class)
    public R<String> noLoginException(SaTokenException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.warn("请求地址 : 【{}】，API用户未登录 : {}", requestURI, e.getMessage());
        return R.noLogin(e.getMessage());
    }

    /**
     * 拦截API异常
     */
    @ExceptionHandler(ApiException.class)
    public R<String> apiException(ApiException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址 : 【{}】，API异常 : {}", requestURI, e.getMessage());
        return R.fail(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public R<String> handleBindException(BindException e)
    {
        log.warn("自定义验证异常，异常类 : 【{}】 ，异常信息 : {}", e.getAllErrors().get(0).getObjectName(), e.getAllErrors().get(0).getDefaultMessage());
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return R.fail(message);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<String> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                         HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return R.fail(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R<String> handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return R.fail(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return R.fail(e.getMessage());
    }

    /**
     * 权限校验异常（ajax请求返回json，redirect请求跳转页面）
     */
    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        if (ServletUtils.isAjaxRequest(request))
        {
            return AjaxResult.error(PermissionUtils.getMsg(e.getMessage()));
        } else
        {
            return new ModelAndView("error/unauth");
        }
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public Object handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request))
        {
            return AjaxResult.error(e.getMessage());
        } else
        {
            return new ModelAndView("error/service", "errorMessage", e.getMessage());
        }
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e)
    {
        return AjaxResult.error("演示模式，不允许操作");
    }
}
