package com.example.springcontext.aop.jdk;

/**
 * @author Hodur
 * @since 2021-05-27
 */
public interface JdkInterface {

    Integer act(Integer... a);

    Integer actProxy(Integer... a);

    Integer doAct(Integer... a);
}
