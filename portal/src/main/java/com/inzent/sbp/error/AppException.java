package com.inzent.sbp.error;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class AppException extends RuntimeException
{
    @Getter
    private ErrorCode errorCode;

    public AppException() {
        this.errorCode = ErrorCode.UNKNOWN;
    }
    public AppException(ErrorCode code) {
        super();
        this.errorCode = code;
    }

    @Override
    public String getLocalizedMessage() {
        return errorCode.getMessage();
    }

    /**
     * Get the root cause.
     */
    public Throwable getRootCause()
    {
        Throwable cause = this;
        for (Throwable tmp = this; tmp != null ; tmp = cause.getCause())
        {
            cause = tmp;
        }
        return cause;
    }
}
