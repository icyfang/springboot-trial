package com.example.springcontext.aop.thisPoint;

import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-25
 */
@Component
public class ThisInterfaceImpl implements ThisInterface {

    @Override
    public void act() {
        System.out.println("ThisInterfaceImpl act");
    }
}
