package com.example.demo.context.import1;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class BeanBImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

//        Map<String, Object> map = importingClassMetadata.getAnnotationAttributes(EnableEnableImportSelector.class.getName(), true);
//        if (map != null) {
//
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                System.out.println("key is : " + entry.getKey() + " value is : " + entry.getValue());
//            }
//        }


        return new String[]{BeanB.class.getName()};
    }
}

