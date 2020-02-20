package com.exam.file.util;

import java.util.UUID;

public class UUIDUtil {

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }
}
