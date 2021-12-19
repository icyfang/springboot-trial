package com.example.springcontext.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo of factory in bean definition, using instance factory
 *
 * @author Hodur
 * @date 2021/10/12
 */
public class CarInstanceFactory {
    private Map<Integer, Car> map = new HashMap<>();

    public void setMap(Map<Integer, Car> map) {
        this.map = map;
    }

    public CarInstanceFactory() {
    }

    public Car getCar(int id) {
        return map.get(id);
    }
}
