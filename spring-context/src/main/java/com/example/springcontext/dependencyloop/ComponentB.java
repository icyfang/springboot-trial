package com.example.springcontext.dependencyloop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-28
 */
@Component
public class ComponentB {

    @Autowired
    private ComponentA componentA;
}
