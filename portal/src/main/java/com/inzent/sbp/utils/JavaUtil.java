package com.inzent.sbp.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class JavaUtil {
    public static <T> T defaultWhenNull(@Nullable T object, @NonNull T def) {
        return (object == null) ? def : object;
    }
}
