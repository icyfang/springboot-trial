package com.example.springcontext.aop.thisPoint;

/**
 * @author Hodur
 * @since 2021-05-25
 */
public interface ThisInterface {

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
