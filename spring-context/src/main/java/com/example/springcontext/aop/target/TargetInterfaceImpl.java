package com.example.springcontext.aop.target;

import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-25
 */
@Component
public class TargetInterfaceImpl implements TargetInterface {

    @Override
    public void act() {
        System.out.println("TargetInterface act");
    }

}
