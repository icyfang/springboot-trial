package com.example.springcontext.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/10/12
 */
public class CarInstanceFactory {
    private Map<Integer, Car> map = new HashMap<Integer, Car>();

    public void setMap(Map<Integer, Car> map) {
        this.map = map;
    }

    public CarInstanceFactory() {
    }

    public Car getCar(int id) {
        return map.get(id);
    }
}
