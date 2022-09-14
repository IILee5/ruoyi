package com.ruoyi.common.utils;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * API工具类
 *
 * @author ruoyi
 */
@Component
public class ApiUtils
{
    /**
     * 设置API缓存
     *
     * @param apiKey         API KEY
     * @param apiSecret API Secret
     */
    public static void setApiCache(String apiKey, String apiSecret)
    {
        SpringUtils.getBean(RedisCache.class).setCacheObject(getCacheKey(apiKey), apiSecret);
    }

    /**
     * 根据API KEY获取API Secret
     *
     * @param apiKey API KEY
     * @return API Secret
     */
    public static String getApiSecret(String apiKey)
    {
        return SpringUtils.getBean(RedisCache.class).getCacheObject(getCacheKey(apiKey));
    }

    /**
     * 删除指定API缓存
     *
     * @param apiKey API KEY
     */
    public static void removeApiCache(String apiKey)
    {
        SpringUtils.getBean(RedisCache.class).deleteObject(getCacheKey(apiKey));
    }

    /**
     * 清空API缓存
     */
    public static void clearApiCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(Constants.SYS_API_KEY + "*");
        SpringUtils.getBean(RedisCache.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     *
     * @param configKey API KEY
     * @return 缓存键key
     */
    private static String getCacheKey(String configKey)
    {
        return Constants.SYS_API_KEY + configKey;
    }
}
