package com.example.jpa.association.onetomany.bidirectional;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Data
@Entity(name = "onetomany_sub")
public class SubPO {
    @Id
    private Long id;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "onetomany_join", joinColumns = @JoinColumn(name = "sub_id"), inverseJoinColumns = @JoinColumn(name = "main_id"))
    private MainPO mainPO;
}
