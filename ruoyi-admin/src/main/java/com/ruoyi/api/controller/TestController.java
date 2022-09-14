package com.ruoyi.api.controller;

import com.baomidou.lock.annotation.Lock4j;
import com.ruoyi.api.asyn.AsyncTask;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.api.mq.MQHandler;
import com.ruoyi.model.UserEntity;
import com.ruoyi.model.dto.Job;
import com.ruoyi.model.dto.OrderDelayQueueDTO;
import com.ruoyi.model.flow.PayContext;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * swagger 用户测试方法
 *
 * @author ruoyi
 */
@Api(tags = "用户信息管理")
@RestController
@RequestMapping("/api/user")
public class TestController extends BaseController
{
    @Resource
    private MQHandler mqHandler;
    @Resource
    private FlowExecutor flowExecutor;
    @Resource
    private AsyncTask asyncTask;

    private final static Map<Integer, UserEntity> users = new LinkedHashMap<Integer, UserEntity>();

    {
        users.put(1, new UserEntity(1, "admin", "admin123", "15888888888"));
        users.put(2, new UserEntity(2, "ry", "admin123", "15666666666"));
    }

    @ApiOperation("获取用户列表")
    @GetMapping("/list")
    public R<List<UserEntity>> userList()
    {
        List<UserEntity> userList = new ArrayList<UserEntity>(users.values());
        return R.ok(userList);
    }

    @ApiOperation("获取用户详细")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
    @GetMapping("/{userId}")
    public R<UserEntity> getUser(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            return R.ok(users.get(userId));
        } else
        {
            return R.fail("用户不存在");
        }
    }

    @Lock4j(keys = {"#user.userId"})
    @ApiOperation("新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "Integer", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "password", value = "用户密码", dataType = "String", dataTypeClass = String.class),
            @ApiImplicitParam(name = "mobile", value = "用户手机", dataType = "String", dataTypeClass = String.class)
    })
    @PostMapping("/save")
    public R<String> save(UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return R.fail("用户ID不能为空");
        }
        users.put(user.getUserId(), user);
        return R.ok();
    }

    @Lock4j(keys = {"#user.userId"})
    @ApiOperation("更新用户")
    @PutMapping("/update")
    public R<String> update(@RequestBody UserEntity user)
    {
        if (StringUtils.isNull(user) || StringUtils.isNull(user.getUserId()))
        {
            return R.fail("用户ID不能为空");
        }
        if (users.isEmpty() || !users.containsKey(user.getUserId()))
        {
            return R.fail("用户不存在");
        }
        users.remove(user.getUserId());
        users.put(user.getUserId(), user);
        return R.ok();
    }

    @ApiOperation("删除用户信息")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "path", dataTypeClass = Integer.class)
    @DeleteMapping("/{userId}")
    public R<String> delete(@PathVariable Integer userId)
    {
        if (!users.isEmpty() && users.containsKey(userId))
        {
            users.remove(userId);
            return R.ok();
        } else
        {
            return R.fail("用户不存在");
        }
    }

    @GetMapping("/orderTest")
    public R<String> orderDelayQueueTest(@RequestParam("bizId") String bizId)
    {
        OrderDelayQueueDTO orderDelayQueueDTO = OrderDelayQueueDTO.builder()
                .orderId(bizId)
                .build();
        mqHandler.delayPush(orderDelayQueueDTO);
        return R.ok();
    }

    @GetMapping("/orderDelTest")
    public R<List<OrderDelayQueueDTO>> orderDelayQueueDelTest(@RequestParam("bizId") String bizId)
    {
        OrderDelayQueueDTO orderDelayQueueDTO = OrderDelayQueueDTO.builder()
                .orderId(bizId)
                .build();
        List<OrderDelayQueueDTO> del = mqHandler.delayDel(orderDelayQueueDTO, OrderDelayQueueDTO.class);
        return R.ok(del);
    }

    @GetMapping("/payCallback")
    public R<String> payCallback(@RequestParam("bizId") String bizId)
    {
        LiteflowResponse aliResponse = flowExecutor.execute2Resp("aliPayCallbackChain", bizId, PayContext.class);
        LiteflowResponse wxResponse = flowExecutor.execute2Resp("wxPayCallbackChain", bizId, PayContext.class);
        logger.info("aliResponse : {}", aliResponse.getContextBean(PayContext.class).getContext());
        logger.info("wxResponse : {}", wxResponse.getContextBean(PayContext.class).getContext());
        return R.ok();
    }

    @GetMapping("/mQSender")
    public R<String> mQSender()
    {
        HashMap<String, Object> param = new HashMap<>(3);
        param.put("id", 1);
        param.put("name", "张三");
        param.put("paramInline", new HashMap<>());
        Job job = new Job();
        job.setId(1);
        job.setParam(param);
        job.setCreateDate(new Date());
        job.setEndDate(LocalDateTime.now());
        mqHandler.push(job);
        return R.ok();
    }

    @GetMapping("/doAsyn")
    public R<String> doAsyn()
    {
        asyncTask.doTask1();
        return R.ok();
    }

}
