package com.example.springcontext.aop.target;

/**
 * @author Hodur
 * @since 2021-05-25
 */
public interface TargetInterface {

    /**
     * act
     */
    void act();

    /**
     * do act
     */
    default void doAct() {
        System.out.println("default act");
    }
}
