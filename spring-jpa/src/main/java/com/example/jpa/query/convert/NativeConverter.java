package com.example.jpa.query.convert;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.lang.Nullable;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Hodur
 * @date 2021/7/9
 */
public class NativeConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(Map.class, Object.class));
    }

    @Override
    @Nullable
    public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Map<?, ?> sMap = (Map<?, ?>) source;
        BeanWrapper bw = new BeanWrapperImpl(targetType.getObjectType());
        PropertyDescriptor[] pds = bw.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String s = pd.getName();
            if (bw.isWritableProperty(s) && sMap.containsKey(s)) {
                if (sMap.get(s) != null) {
                    bw.setPropertyValue(s, sMap.get(s));
                }
            }
        }
        return bw.getWrappedInstance();
    }
}
