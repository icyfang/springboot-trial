package com.example.springcontext.conditional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hodur
 * @date 2020-08-06
 */
@Slf4j
@Configuration
public class SystemBeanConfig {

    @Bean("windows")
    @Conditional({ConditionForWindows.class})
    public SystemBean windows() {
        log.info("ConditionalConfig方法注入 windows实体");
        return new SystemBean("windows系统","002");
    }
    /**
     * 如果LinuxCondition的实现方法返回true，则注入这个bean
     */
    @Bean("mac")
    @Conditional({ConditionForMac.class})
    public SystemBean mac() {
        log.info("ConditionalConfig方法注入 mac实体");
        return new SystemBean("Mac ios系统","001");
    }
}
