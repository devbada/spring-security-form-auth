package io.security.basicsecurity.security.repository;

import io.security.basicsecurity.security.entity.AccessIp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @since       2022.04.11
 * @author      minam
 * @description access ip repository
 **********************************************************************************************************************/
public interface AccessIpRepository extends JpaRepository<AccessIp, Long> {

    Optional<AccessIp> findByIpAddress(String ipAddress);
}