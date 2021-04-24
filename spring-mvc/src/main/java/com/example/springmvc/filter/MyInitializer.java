package com.example.springmvc.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;

/**
 * @author Hodur
 * @since 2020-08-05
 */
@Slf4j
@Configuration
public class MyInitializer implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic myFilter = servletContext.addFilter("myFilter", MyFilter.class);
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);

        dispatcherTypes.add(DispatcherType.REQUEST);
        dispatcherTypes.add(DispatcherType.FORWARD);
        myFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/advice/v2");

        log.error("MyInitializer");
    }

}
