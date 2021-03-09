package com.jj.collaboss.error;

import com.jj.collaboss.utils.Context;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Locale;

import static com.jj.collaboss.utils.JavaUtil.defaultWhenNull;

public enum ErrorCode {

    UNKNOWN(999),

    GENERAL(500),
    NOT_ACCEPTABLE(406),

    SENDING_EMAIL(800),

    NOT_FOUND(404),
    CONFLICT(409),
    ;

    private final int value;
    private Object[] params;
    private Locale locale;

    ErrorCode(int value) {
        this(value, null, null);
    }

    ErrorCode(int value, Object[] params) {
        this(value, params, null);
    }
    ErrorCode(int value, Object[] params, Locale locale) {
        this.value = value;
        this.params = params;
        this.locale = locale;
    }

    public int value() {
        return this.value;
    }

    public String getMessageKey() {
        return "error." + this.name();
    }

    public String getMessage() {
        String message = Context.getMessage(getMessageKey(), this.params, defaultWhenNull(this.locale, LocaleContextHolder.getLocale()));
        if (StringUtils.isEmpty(message)) {
            return this.name();
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