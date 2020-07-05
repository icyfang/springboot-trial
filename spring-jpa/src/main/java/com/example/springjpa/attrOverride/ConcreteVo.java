package com.example.springjpa.attrOverride;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_concrete")
public class ConcreteVo extends BaseVo {

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private String desp;
}
