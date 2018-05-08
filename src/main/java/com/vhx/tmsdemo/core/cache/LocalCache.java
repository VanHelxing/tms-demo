package com.vhx.tmsdemo.core.cache;

/**
 * 开发环境下建议使用本地缓存用户信息
 */
public class LocalCache implements Cache {


    @Override
    public <T> T getObject(String key) {
        return null;
    }

    @Override
    public <T> T setObejct(String key) {
        return null;
    }
}
