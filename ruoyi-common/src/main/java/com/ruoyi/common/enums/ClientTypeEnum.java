package com.ruoyi.common.enums;


/**
 * 客户端类型
 *
 * @author stable
 * @since 2020/12/8 9:46
 */

public enum ClientTypeEnum
{

    /**
     * "移动端"
     */
    H5("h5"),
    /**
     * "PC端"
     */
    PC("pc"),
    /**
     * "移动应用端"
     */
    APP("app"),
    ;

    private final String clientName;

    ClientTypeEnum(String des) {
        this.clientName = des;
    }

    public String clientName() {
        return this.clientName;
    }

    public String value() {
        return this.name();
    }
}
