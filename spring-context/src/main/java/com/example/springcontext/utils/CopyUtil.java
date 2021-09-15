package com.example.springcontext.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Arrays;
import java.util.Map;

/**
 * @author Hodur
 * @date 2021/9/15
 */
public class CopyUtil {

    /**
     * fill object with map
     *
     * @param o   o
     * @param map map
     * @return T
     */
    public static <T> T fillObjectWithMap(T o, Map<String, Object> map) {
        if (o == null || map == null) {
            return o;
        }
        BeanWrapper bw = new BeanWrapperImpl(o);
        Arrays.stream(bw.getPropertyDescriptors())
              .map(FeatureDescriptor::getName)
              .filter(i -> bw.isWritableProperty(i) && map.containsKey(i) && map.get(i) != null)
              .forEach(i -> bw.setPropertyValue(i, map.get(i)));
        return o;
    }

}

