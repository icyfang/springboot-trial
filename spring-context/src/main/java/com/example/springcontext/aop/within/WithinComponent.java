package com.example.springcontext.aop.within;

import com.example.springcontext.aop.CustomAnn;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-23
 */
@Component
public class WithinComponent {

    public void act() {
        System.out.println("WithinComponent act");
    }

    @CustomAnn("doAct")
    public void doAct() {
        System.out.println("WithinComponent doAct");
    }
}
