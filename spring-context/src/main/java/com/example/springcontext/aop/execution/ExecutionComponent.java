package com.example.springcontext.aop.execution;

import com.example.springcontext.aop.CustomAnn;
import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-26
 */
@Component
public class ExecutionComponent {

    @CustomAnn("act")
    public void act() {
        System.out.println("ExecutionComponent act");
    }

    public void act(@CustomAnn("param") String a) {
        System.out.println("ExecutionComponent act");
    }
}
