package com.inzent.sbp.error;

import com.inzent.sbp.utils.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Locale;

import static com.inzent.sbp.utils.JavaUtil.defaultWhenNull;

public enum ErrorCode {

    UNKNOWN(999, "error.UnknownServerError"),

    GENERAL(100, "error.GeneralError"),
    NOT_FOUND(404, "error.NotFound"),
    ;

    private final int value;
    private final String messageKey;
    private Object[] params;
    private Locale locale;

    ErrorCode(int value, String messageKey) {
        this(value, messageKey, null, null);
    }

    ErrorCode(int value, String messageKey, Object[] params) {
        this(value, messageKey, params, null);
    }
    ErrorCode(int value, String messageKey, Object[] params, Locale locale) {
        this.value = value;
        this.messageKey = messageKey;
        this.params = params;
        this.locale = locale;
    }

    public int value() {
        return this.value;
    }

    public String getMessageKey() {
        return this.messageKey;
    }

    public String getMessage() {
        String message = Context.getMessage(this.messageKey, this.params, defaultWhenNull(this.locale, LocaleContextHolder.getLocale()));
        if (StringUtils.isEmpty(message)) {
            return this.messageKey;
        }
        return message;
    }

    public String toString() {
        return this.value + " " + this.name();
    }

    public static ErrorCode valueOf(int statusCode) {
        ErrorCode status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        } else {
            return status;
        }
    }

    @Nullable
    public static ErrorCode resolve(int statusCode) {
        ErrorCode[] var1 = values();

        for (ErrorCode status : var1) {
            if (status.value == statusCode) {
                return status;
            }
        }

        return null;
    }
}