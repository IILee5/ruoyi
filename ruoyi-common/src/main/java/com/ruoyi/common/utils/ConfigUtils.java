package com.ruoyi.common.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 参数工具类
 *
 * @author ruoyi
 */
@Component
public class ConfigUtils
{
    /**
     * 设置参数缓存
     *
     * @param key         参数键
     * @param configValue 参数值
     */
    public static void setConfigCache(String key, String configValue)
    {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(key), configValue);
    }

    /**
     * 根据参数键获取参数值
     *
     * @param key 参数键
     * @return 参数值
     */
    public static String getConfigValue(String key)
    {
        return SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(key));
    }

    /**
     * 删除指定参数缓存
     *
     * @param key 参数键
     */
    public static void removeConfigCache(String key)
    {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(key));
    }

    /**
     * 清空参数缓存
     */
    public static void clearConfigCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_CONFIG_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private static String getCacheKey(String configKey)
    {
        return Constants.SYS_CONFIG_KEY + configKey;
    }
}
