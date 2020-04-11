package com.example.demo.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "concrete")
public class ConcVo extends BaseVo {

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private String desp;
}
