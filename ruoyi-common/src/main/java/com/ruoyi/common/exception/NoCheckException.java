package com.ruoyi.common.exception;

/**
 * 签名失败异常
 *
 * @author ruoyi
 */
public final class NoCheckException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public NoCheckException()
    {
    }

    public NoCheckException(String message)
    {
        this.message = message;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    public NoCheckException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public NoCheckException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}
