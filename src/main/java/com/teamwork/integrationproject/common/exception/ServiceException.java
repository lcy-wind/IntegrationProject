package com.teamwork.integrationproject.common.exception;

import com.teamwork.integrationproject.utils.resposnse.ResultCode;
import lombok.Getter;

/**
 * Author Anyho(wuh@infoepoh.com)
 * Time   2019/12/16 16:01 星期一
 */
public class ServiceException extends RuntimeException
{
    @Getter
    private final ResultCode resultCode;

    public ServiceException(String message)
    {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(ResultCode resultCode)
    {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String msg)
    {
        super(msg);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, Throwable cause)
    {
        super(cause);
        this.resultCode = resultCode;
    }

    public ServiceException(String msg, Throwable cause)
    {
        super(msg, cause);
        this.resultCode = ResultCode.FAILURE;
    }

    /**
     * for better performance
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace()
    {
        return this;
    }

    public Throwable doFillInStackTrace()
    {
        return super.fillInStackTrace();
    }
}
