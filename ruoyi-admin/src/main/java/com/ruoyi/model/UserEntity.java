package com.ruoyi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 测试实体
 *
 * @author huhaiqiang
 * @date 2022-08-23 16:36
 */
@ApiModel(value = "UserEntity", description = "用户实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity
{
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户手机")
    private String mobile;

}
