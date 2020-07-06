package com.example.springjpa.attrOverride;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_concrete2")
@AttributeOverride(name = "name", column = @Column(name = "name"))
public class ConcreteVo2 extends BaseVo {

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    private String desp;
}
