package com.ruoyi.component;

import com.ruoyi.model.flow.PayContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * 初始化参数检查组件
 *
 * @author huhaiqiang
 * @date 2022-09-09 11:10
 */
@Slf4j
@LiteflowComponent("aliCheckCmp")
public class AliCheckCmp extends NodeComponent
{
    @Override
    public void process() throws Exception
    {
        String bizId = this.getSlot().getRequestData();
        PayContext contextBean = this.getContextBean(PayContext.class);
        contextBean.setContext("aliCheckCmp");
        log.info("aliCheckCmp success , bizId : {}", bizId);
    }
}
