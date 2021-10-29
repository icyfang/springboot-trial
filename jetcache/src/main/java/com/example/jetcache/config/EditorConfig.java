package com.example.jetcache.config;

import com.alicp.jetcache.Cache;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * @author Hodur
 * @date 2021/10/28
 */
@Configuration
public class EditorConfig {

    @Bean
    CustomEditorConfigurer cacheEditorConfigurer() {
        CustomEditorConfigurer configurer = new CustomEditorConfigurer();
        configurer.setCustomEditors(Collections.singletonMap(Cache.class, CacheEditor.class));
        return configurer;
    }

}
