package com.example.jpa.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @date 2021-04-28
 */
@Service
public class CalleeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class
            , transactionManager = "jdbcTransactionManager")
    public void requiresNew(boolean ex) {
        saveConcretePO();
        if (ex) {
            throw new RuntimeException();
        }
    }

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class
            , transactionManager = "jdbcTransactionManager")
    public void nested(boolean ex) {
        saveConcretePO();
        if (ex) {
            throw new RuntimeException();
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class
            , transactionManager = "jdbcTransactionManager")
    public void notSupported(boolean ex) {
        saveConcretePO();
        if (ex) {
            throw new RuntimeException();
        }
    }

    public void saveConcretePO() {

        jdbcTemplate.update("insert into t_concrete2(name, desp) values(?,?)", "description", "name");
    }
}