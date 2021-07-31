package com.example.springcontext.aop.jdk;

/**
 * @author Hodur
 * @since 2021-05-27
 */
public interface JdkInterface {

    /**
     * act
     *
     * @param a a
     * @return java.lang.Integer
     */
    Integer act(Integer... a);

    /**
     * act proxy
     *
     * @param a a
     * @return java.lang.Integer
     */
    Integer actProxy(Integer... a);

    /**
     * do act
     *
     * @param a a
     * @return java.lang.Integer
     */
    Integer doAct(Integer... a);
}
