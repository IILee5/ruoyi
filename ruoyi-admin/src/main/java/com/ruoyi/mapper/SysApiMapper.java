package com.ruoyi.mapper;

import com.ruoyi.model.domain.SysApi;

import java.util.List;

/**
 * API密钥Mapper接口
 *
 * @author iilee
 * @date 2022-09-06
 */
public interface SysApiMapper
{
    /**
     * 查询API密钥
     *
     * @param apiId API密钥主键
     * @return API密钥
     */
    public SysApi selectSysApiByApiId(Integer apiId);

    /**
     * 通过用户编号查询API密钥
     *
     * @param userId 用户编号
     * @return API密钥
     */
    public SysApi selectSysApiByApiUserId(Long userId);

    /**
     * 查询API密钥列表
     *
     * @param sysApi API密钥
     * @return API密钥集合
     */
    public List<SysApi> selectSysApiList(SysApi sysApi);

    /**
     * 新增API密钥
     *
     * @param sysApi API密钥
     * @return 结果
     */
    public int insertSysApi(SysApi sysApi);

    /**
     * 修改API密钥
     *
     * @param sysApi API密钥
     * @return 结果
     */
    public int updateSysApi(SysApi sysApi);

    /**
     * 删除API密钥
     *
     * @param apiId API密钥主键
     * @return 结果
     */
    public int deleteSysApiByApiId(Integer apiId);

    /**
     * 批量删除API密钥
     *
     * @param apiIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysApiByApiIds(String[] apiIds);
}
