package com.jj.sbp.error;

import lombok.Getter;

public class AppException extends RuntimeException
{
    @Getter
    private ErrorCode errorCode;

    @Getter
    private String description;

    public AppException() {
        this.errorCode = ErrorCode.UNKNOWN;
    }
    public AppException(ErrorCode code) {
        this(code, "");
    }

    public AppException(ErrorCode code, String description) {
        super(code.getMessage());
        this.errorCode = code;
        this.description = description;
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
