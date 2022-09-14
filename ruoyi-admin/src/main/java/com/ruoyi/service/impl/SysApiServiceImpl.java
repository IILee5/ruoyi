package com.ruoyi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ApiUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.mapper.SysApiMapper;
import com.ruoyi.model.domain.SysApi;
import com.ruoyi.service.ISysApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

/**
 * API密钥Service业务层处理
 *
 * @author iilee
 * @date 2022-09-06
 */
@Service
public class SysApiServiceImpl implements ISysApiService
{
    @Autowired
    private SysApiMapper sysApiMapper;

    /**
     * 项目启动时，初始化API信息到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingApiCache();
    }

    /**
     * 查询API密钥
     *
     * @param apiId API密钥主键
     * @return API密钥
     */
    @Override
    public SysApi selectSysApiByApiId(Integer apiId)
    {
        return sysApiMapper.selectSysApiByApiId(apiId);
    }

    @Override
    public SysApi selectSysApiByApiUserId(Long userId)
    {
        return sysApiMapper.selectSysApiByApiUserId(userId);
    }

    /**
     * 查询API密钥列表
     *
     * @param sysApi API密钥
     * @return API密钥
     */
    @Override
    public List<SysApi> selectSysApiList(SysApi sysApi)
    {
        return sysApiMapper.selectSysApiList(sysApi);
    }

    /**
     * 新增API密钥
     *
     * @param sysApi API密钥
     * @return 结果
     */
    @Override
    public int insertSysApi(SysApi sysApi)
    {
        SysApi api = sysApiMapper.selectSysApiByApiUserId(ShiroUtils.getUserId());
        if (!Objects.isNull(api))
        {
            throw new ServiceException("用户已有密钥，不可重复申请");
        }
        IdUtil.nanoId();
        sysApi.setCreateBy(ShiroUtils.getLoginName());
        sysApi.setCreateTime(DateUtils.getNowDate());
        sysApi.setApiKey(IdUtils.snowflakeNextId());
        sysApi.setApiSecret(IdUtils.nanoId());
        // 默认停用
        sysApi.setApiStatus(UserConstants.NORMAL_DISABLE);
        // 默认为APP的API
        sysApi.setApiType("1");
        int row = sysApiMapper.insertSysApi(sysApi);
        if (row > 0)
        {
            ApiUtils.setApiCache(sysApi.getApiKey(), sysApi.getApiSecret());
        }
        return row;
    }

    /**
     * 修改API密钥
     *
     * @param sysApi API密钥
     * @return 结果
     */
    @Override
    public int updateSysApi(SysApi sysApi)
    {
        sysApi.setUpdateBy(ShiroUtils.getLoginName());
        sysApi.setUpdateTime(DateUtils.getNowDate());
        int row = sysApiMapper.updateSysApi(sysApi);
        if (row > 0)
        {
            SysApi api = selectSysApiByApiId(sysApi.getApiId());
            if (UserConstants.NORMAL_DISABLE.equals(sysApi.getApiStatus()))
            {
                ApiUtils.removeApiCache(api.getApiKey());
            } else
            {
                ApiUtils.setApiCache(api.getApiKey(), api.getApiSecret());
            }
        }
        return row;
    }

    /**
     * 批量删除API密钥
     *
     * @param ids 需要删除的API密钥主键
     * @return 结果
     */
    @Override
    public int deleteSysApiByApiIds(String ids)
    {
        Integer[] apiIds = Convert.toIntArray(ids);
        for (Integer apiId : apiIds)
        {
            SysApi sysApi = selectSysApiByApiId(apiId);
            if (UserConstants.NORMAL_OPEN.equals(sysApi.getApiStatus()))
            {
                throw new ServiceException("有密钥正在被用户使用，请先停用再删除");
            }
            ApiUtils.removeApiCache(sysApi.getApiKey());
        }
        return sysApiMapper.deleteSysApiByApiIds(Convert.toStrArray(ids));
    }

    /**
     * 删除API密钥信息
     *
     * @param apiId API密钥主键
     * @return 结果
     */
    @Override
    public int deleteSysApiByApiId(Integer apiId)
    {
        SysApi sysApi = selectSysApiByApiId(apiId);
        if (UserConstants.NORMAL_OPEN.equals(sysApi.getApiStatus()))
        {
            throw new ServiceException("密钥正在被用户使用，请先停用再删除");
        }
        ApiUtils.removeApiCache(sysApi.getApiKey());
        return sysApiMapper.deleteSysApiByApiId(apiId);
    }

    @Override
    public void loadingApiCache()
    {
        List<SysApi> sysApiList = sysApiMapper.selectSysApiList(SysApi.builder().build());
        for (SysApi sysApi : sysApiList)
        {
            ApiUtils.setApiCache(sysApi.getApiKey(), sysApi.getApiSecret());
        }
    }

    @Override
    public void clearApiCache()
    {
        ApiUtils.clearApiCache();
    }

    @Override
    public void resetApiCache()
    {
        clearApiCache();
        loadingApiCache();
    }
}
