package com.ruoyi.api.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.ClientTypeEnum;
import com.ruoyi.common.exception.ApiException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.model.UserEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 登录测试
 *
 * @author kong
 */
@RestController
@RequestMapping("/api")
public class LoginController
{

    @RequestMapping("/doLogin")
    public R<SaTokenInfo> doLogin(String username, String password)
    {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if ("zhang".equals(username) && "123456".equals(password))
        {
            // 第1步，先登录上
            StpUtil.login(10001, ClientTypeEnum.APP.clientName());
            // 第2步，获取 Token  相关参数
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return R.ok(tokenInfo);
        }
        return R.fail("用户名或密码错误");
    }

    @RequestMapping("/checkLogin")
    public R<String> checkLogin()
    {
        StpUtil.checkLogin();
        return R.ok("用户已登录");
    }

    @RequestMapping("/tokenInfo")
    public R<SaTokenInfo> tokenInfo()
    {
        return R.ok(StpUtil.getTokenInfo());
    }

    @RequestMapping("/logout")
    public R<String> logout()
    {
        StpUtil.logout();
        return R.ok();
    }

}
