package com.example.springcontext.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo of factory in bean definition, using static factory
 *
 * @author Hodur
 * @date 2021/10/12
 */
public class CarStaticFactory {
    private static final Map<Integer, Car> CAR_MAP = new HashMap<>();

    static {
        CAR_MAP.put(1, new Car(1, "Honda", 300000));
        CAR_MAP.put(2, new Car(2, "Audi", 440000));
        CAR_MAP.put(3, new Car(3, "BMW", 540000));
    }

    public static Car getCar(int id) {
        return CAR_MAP.get(id);
    }
}
