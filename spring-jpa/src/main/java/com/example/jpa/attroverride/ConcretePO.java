package com.example.jpa.attroverride;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_concrete")
public class ConcretePO extends BasePO {

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private String desp;
}
