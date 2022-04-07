package io.security.basicsecurity.admin.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * @since       2022.03.24
 * @author      minam
 * @description account
 **********************************************************************************************************************/

 @Entity
 @Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    private String email;

    private Integer age;

    private String role;

//    @OneToMany
//    private Set<Role> roles;
}