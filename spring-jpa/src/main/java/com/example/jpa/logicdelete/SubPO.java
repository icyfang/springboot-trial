package com.example.jpa.logicdelete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Hodur
 * @date 2021/07/16
 */
@Data
@Entity(name = "logic_delete_sub")
@SQLDelete(sql = "update logic_delete_sub set deleted = 0 where id = ?")
@Where(clause = " deleted = 1")
@AllArgsConstructor
@NoArgsConstructor
public class SubPO {
    @Id
    private Long id;
    private String name;
    private Integer deleted = 1;
}
