package io.security.basicsecurity.security.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @since       2022.04.07
 * @author      minam
 * @description resources
 **********************************************************************************************************************/
@Entity
@Table(name = "RESOURCES")
@Data
@ToString(exclude = {"roleSet"})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resources implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "order_num")
    private int orderNum;

    @Column(name = "resource_type")
    private String resourceType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_resources", joinColumns = {
            @JoinColumn(name = "resource_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private Set<Role> roleSet = new HashSet<>();

}
