package com.example.springcontext.aop.args;

import org.springframework.stereotype.Component;

/**
 * @author Hodur
 * @since 2021-05-25
 */
@Component
public class ArgsComponent {

    public void act(String a, String b) {
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        System.out.println("ArgsComponent act");
    }

    public void act(AnnotatedClass a, Integer b) {
        System.out.println("ArgsComponent act 2");
    }

    public void act(AnnotatedClass a) {
        System.out.println("ArgsComponent act 3");
    }
}
