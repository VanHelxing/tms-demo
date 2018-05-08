package com.vhx.tmsdemo.core.cache;

/**
 * 生产环境下建议使用Redis作为缓存用户信息
 */
public class RedisCache implements Cache {

    @Override
    public <T> T getObject(String key) {
        return null;
    }

    @Override
    public <T> T setObejct(String key) {
        return null;
    }
}
