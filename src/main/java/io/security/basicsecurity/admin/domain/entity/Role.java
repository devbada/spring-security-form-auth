package io.security.basicsecurity.admin.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @since       2022.04.07
 * @author      minam
 * @description role
 **********************************************************************************************************************/

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}