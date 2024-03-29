package com.example.springcontext.factory;

/**
 * @author Hodur
 * @date 2021/10/12
 */
public class Car {

    private int id;
    private String name;
    private int price;

    public Car() {
    }

    public Car(int id, String name, int price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
