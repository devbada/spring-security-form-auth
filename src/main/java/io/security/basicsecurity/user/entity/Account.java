package io.security.basicsecurity.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
}