package com.example.jpa.logicdelete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author Hodur
 * @date 2021/07/16
 */
@Data
@Entity(name = "logic_delete_main")
@Proxy(lazy = false)
@SQLDelete(sql = "update logic_delete_main set deleted = 0 where id = ?")
@Where(clause = " deleted = 1")
@AllArgsConstructor
@NoArgsConstructor
public class MainPO {

    @Id
    private Long id;
    private String content;
    private Integer deleted = 1;

    @Where(clause = " deleted = 1")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "main_id")
    private List<SubPO> subPOS;
}
