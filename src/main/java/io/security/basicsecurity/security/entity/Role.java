package io.security.basicsecurity.security.entity;

import io.security.basicsecurity.admin.domain.entity.Account;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @since       2022.04.07
 * @author      minam
 * @description role
 **********************************************************************************************************************/


@Entity
@Table(name = "ROLE")
@Data
@ToString(exclude = {"accounts","resourcesSet"})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Role implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", unique=true)
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roleSet")
    @OrderBy("orderNum desc")
    private Set<Resources> resourcesSet = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private Set<Account> accounts = new HashSet<>();

}

