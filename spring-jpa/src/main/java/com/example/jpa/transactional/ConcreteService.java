package com.example.jpa.transactional;

import com.example.jpa.attrOverride.ConcretePO;
import com.example.jpa.attrOverride.ConcreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @since 2021-04-28
 */
@Service
public class ConcreteService {

    @Autowired
    private ConcreteRepository concreteRepository;
    @Autowired
    private ConcreteService2 concreteService2;

    @Transactional
    public void requireNew1() {
        saveConcretePO();
        concreteService2.saveConcretePO2(false);
        throw new RuntimeException();
    }

    @Transactional
    public void requireNew2() {
        saveConcretePO();
        concreteService2.saveConcretePO2(true);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested1() {
        saveConcretePO();
        concreteService2.saveConcretePO2(false);
        throw new RuntimeException();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void nested2() {
        saveConcretePO();
        concreteService2.saveConcretePO2(false);
    }

    private void saveConcretePO() {
        ConcretePO concretePO = new ConcretePO();
        concretePO.setDesp("a");
        concretePO.setName("a");
        concreteRepository.save(concretePO);

    }

}
