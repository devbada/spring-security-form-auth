package io.security.basicsecurity.admin.domain.entity;

import io.security.basicsecurity.security.entity.Role;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @since       2022.03.24
 * @author      minam
 * @description account
 **********************************************************************************************************************/

@Entity
@Data
@ToString(exclude = {"roles"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    private String email;

    private Integer age;

    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roles;
}