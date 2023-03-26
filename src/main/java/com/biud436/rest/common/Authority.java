package com.biud436.rest.common;

public enum Authority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    Authority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

