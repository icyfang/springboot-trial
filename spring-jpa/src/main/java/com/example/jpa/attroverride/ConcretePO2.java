package com.example.jpa.attroverride;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Hodur
 * @date 2020/04/10
 */
@Entity
@Table(name = "t_concrete2")
@AttributeOverride(name = "name", column = @Column(name = "name"))
public class ConcretePO2 extends BasePO {

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private String desp;
}
