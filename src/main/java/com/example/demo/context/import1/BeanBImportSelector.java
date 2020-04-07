package com.example.demo.context.import1;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class BeanBImportSelector implements ImportSelector {

    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{BeanB.class.getName()};
    }
}

