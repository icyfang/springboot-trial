package com.example.jpa.transactional;

import com.example.jpa.attrOverride.ConcretePO2;
import com.example.jpa.attrOverride.ConcreteRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @date 2021-04-28
 */
@Service
public class ConcreteService2 {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private ConcreteRepository2 concreteRepository2;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveConcretePO2(boolean ex) {
        ConcretePO2 concretePO2 = new ConcretePO2();
        concretePO2.setDesp("a");
        concretePO2.setName("a");
        concreteRepository2.save(concretePO2);
        if (ex) {
            throw new RuntimeException();
        }
    }
}
