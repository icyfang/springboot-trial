package com.example.jetcache.config;

import com.example.jetcache.common.CacheHolder;

import java.beans.PropertyEditorSupport;

/**
 * @author Hodur
 * @date 2021/10/28
 */
public class CacheEditor extends PropertyEditorSupport {

    public CacheEditor() {
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof CacheHolder) {
            CacheHolder<?, ?> c = (CacheHolder<?, ?>) value;
            super.setValue(c.getCache());
        } else {
            throw new IllegalArgumentException("Editor supports only conversion of type " + CacheHolder.class);
        }
    }
}
