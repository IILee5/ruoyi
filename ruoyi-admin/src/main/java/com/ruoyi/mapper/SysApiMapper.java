package com.ruoyi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.model.domain.SysApi;

import java.util.List;

/**
 * API密钥Mapper接口
 *
 * @author iilee
 * @date 2022-09-06
 */
public interface SysApiMapper extends BaseMapper<SysApi>
{
    /**
     * 查询API密钥列表
     *
     * @param sysApi API密钥
     * @return API密钥集合
     */
    public List<SysApi> selectSysApiList(SysApi sysApi);
}
