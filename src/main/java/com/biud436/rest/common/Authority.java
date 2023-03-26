package com.biud436.rest.common;

public enum Authority {
    USER("USER"),
    ADMIN("ADMIN"),
    GUEST("GUEST");

    private final String value;

    Authority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

