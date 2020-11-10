package com.example.springmvc.jsr303;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidUser {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 5, max = 10, message = "{validation.validUser.name.size}", groups = {Batch.class})
    private String name;

    @NotNull
    @Min(value = 18, message = "{validation.validUser.age.min}", groups = {Single.class})
    private int age;

    public interface Single {

    }

    public interface Batch {
    }

}
