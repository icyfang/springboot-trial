package com.example.springcontext.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author Hodur
 * @since 2021-05-28
 */
public class BeanBImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{BeanB.class.getName()};
    }
}

