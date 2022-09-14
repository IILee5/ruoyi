package com.ruoyi.common.utils.uuid;

import cn.hutool.core.util.IdUtil;

/**
 * ID生成器工具类
 *
 * @author ruoyi
 */
public class IdUtils
{
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID()
    {
        return UUID.fastUUID().toString(true);
    }

    /**
     * 简单获取Snowflake 的 nextId 终端ID 数据中心ID 默认为1
     *
     * @return 雪花ID
     */
    public static String snowflakeNextId()
    {
        return IdUtil.getSnowflakeNextIdStr();
    }

    /**
     * 获取随机NanoId
     *
     * @return NanoId
     */
    public static String nanoId()
    {
        return IdUtil.nanoId();
    }
}
