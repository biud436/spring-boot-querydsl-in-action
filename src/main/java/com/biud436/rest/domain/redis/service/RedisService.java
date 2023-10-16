package com.biud436.rest.domain.redis.service;

public interface RedisService {
    void set(String key, String value);
    String get(String key);
}
