package com.example.springcontext.aop.target;

/**
 * @author Hodur
 * @since 2021-05-25
 */
public interface TargetInterface {

    void act();

    default void doAct() {
        System.out.println("default act");
    }
}
