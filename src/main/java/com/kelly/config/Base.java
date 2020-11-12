package com.kelly.config;

import java.util.UUID;

public class Base {

    public boolean isEmp(Object obj) {
        return obj == null || "".equals(String.valueOf(obj)) || "null".equals(String.valueOf(obj))
                || "NULL".equals(String.valueOf(obj));
    }

    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
