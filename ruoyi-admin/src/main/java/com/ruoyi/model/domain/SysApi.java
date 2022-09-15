package com.ruoyi.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.MPBaseEntity;
import lombok.*;

/**
 * API密钥对象 sys_api
 *
 * @author iilee
 * @date 2022-09-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "sys_api")
public class SysApi extends MPBaseEntity
{
    /** API主键 */
    @TableId(type = IdType.AUTO)
    private Long apiId;

    /** 用户ID */
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 用户名称 */
    @TableField(exist = false)
    private String userName;

    /** API钥匙 */
    @Excel(name = "API钥匙")
    private String apiKey;

    /** API密钥 */
    @Excel(name = "API密钥")
    private String apiSecret;

    /** API服务类型 */
    @Excel(name = "API服务类型")
    private String apiType;

    /** API服务状态（0正常 1停用） */
    @Excel(name = "API服务状态", readConverterExp = "0=正常,1=停用")
    private String apiStatus;
}
