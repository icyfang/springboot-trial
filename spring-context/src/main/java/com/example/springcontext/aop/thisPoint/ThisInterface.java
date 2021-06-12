package com.example.springcontext.aop.thisPoint;

/**
 * @author Hodur
 * @since 2021-05-25
 */
public interface ThisInterface {

    void act();

    default void doAct() {
        System.out.println("default act");
    }
}
