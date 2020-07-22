package com.example.springmvc.exception;

/**
 * @author: Shanghong Cai
 * @descirption: customizd exception
 * @create: 2020-07-20 17:13
 */
public class MyException extends Exception {
    public MyException(String msg) {
        super(msg);
    }
}