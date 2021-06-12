package com.example.springcontext.aop.cglib;

import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-23
 */

@Component
public class CglibComponent {

    public Integer act(Integer... a) {
        return doAct(a);
    }

    public Integer doAct(Integer... a) {
        if (a.length > 10) {
            throw new RuntimeException("length exceeds limit");
        }
        return a.length;
    }

    public void defaultAct() {
        System.out.println("default act");
    }
}
