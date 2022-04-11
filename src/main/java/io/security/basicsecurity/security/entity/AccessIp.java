package io.security.basicsecurity.security.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @since       2022.04.11
 * @author      minam
 * @description access ip
 **********************************************************************************************************************/
@Entity
@Table(name="ACCESS_IP")
@Data
@EqualsAndHashCode(of="id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessIp implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="IP_ID", unique=true, nullable=false)
    private Long id;
    
    @Column(name="IP_ADDRESS", nullable=false)
    private String ipAddress;
}