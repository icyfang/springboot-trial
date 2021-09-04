package com.example.jpa.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Hodur
 * @date 2021-04-28
 */
@Service
public class CallerService {

    @Autowired
    private CalleeService calleeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class, transactionManager = "jdbcTransactionManager")
    public void requireNew1() {
        saveConcretePO();
        calleeService.requiresNew(false);
        throw new RuntimeException();
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "jdbcTransactionManager")
    public void requireNew2() {
        saveConcretePO();
        calleeService.requiresNew(true);
    }

    @Transactional(rollbackFor = Exception.class, noRollbackFor = RuntimeException.class
            , transactionManager = "jdbcTransactionManager")
    public void requireNew3() {
        saveConcretePO();
        calleeService.requiresNew(true);
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "jdbcTransactionManager")
    public void nested1() {
        saveConcretePO();
        calleeService.nested(false);
        throw new RuntimeException();
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "jdbcTransactionManager")
    public void nested2() {
        saveConcretePO();
        calleeService.nested(true);
    }

    @Transactional(rollbackFor = Exception.class, noRollbackFor = RuntimeException.class
            , transactionManager = "jdbcTransactionManager")
    public void nested3() {
        saveConcretePO();
        calleeService.nested(true);
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "jdbcTransactionManager")
    public void notSupported1() {
        saveConcretePO();
        calleeService.notSupported(true);
    }

    public void saveConcretePO() {
        jdbcTemplate.update("insert into t_concrete(col_name, desp) values(?,?)", "description", "name");
    }

}