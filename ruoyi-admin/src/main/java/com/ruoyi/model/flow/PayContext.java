package com.ruoyi.model.flow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * liteflow 支付回调上下文
 *
 * @author huhaiqiang
 * @date 2022-09-09 11:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayContext
{
    private String context;
}
