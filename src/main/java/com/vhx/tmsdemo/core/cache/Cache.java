package com.vhx.tmsdemo.core.cache;

public interface Cache {

    public <T>T getObject(String key);

    public <T>T setObejct(String key);
}
