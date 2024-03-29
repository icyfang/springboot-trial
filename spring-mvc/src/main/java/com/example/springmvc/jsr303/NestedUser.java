package com.example.springmvc.jsr303;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NestedUser extends ValidUser {

    public NestedUser(long id, String name, int age, Address address) {
        super(id, name, age);
        this.address = address;
    }

    @NotNull
    @Valid
    private Address address;

    /**
     * @author Hodur
     * @date 2020-11-03
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {

        private Long id;

        @NotNull
        @Size(min = 5, max = 10, message = "{validation.nestedUser.address.name.size}")
        private String country;

    }
}
