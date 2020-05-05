package com.example.demo.jpa.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "concrete")
public class ConcreteVo extends BaseVo {

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private String desp;
}
