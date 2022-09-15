package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.model.domain.SysApi;

import java.util.List;

/**
 * API密钥Service接口
 *
 * @author iilee
 * @date 2022-09-06
 */
public interface ISysApiService extends IService<SysApi>
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
     * 批量删除API密钥
     *
     * @param ids 需要删除的API密钥主键集合
     * @return 结果
     */
    public int deleteSysApiByApiIds(String ids);

    /**
     * 删除API密钥信息
     *
     * @param apiId API密钥主键
     * @return 结果
     */
    public int deleteSysApiByApiId(Integer apiId);

    /**
     * 加载API缓存数据
     */
    public void loadingApiCache();

    /**
     * 清空API缓存数据
     */
    public void clearApiCache();

    /**
     * 重置API缓存数据
     */
    public void resetApiCache();
}
