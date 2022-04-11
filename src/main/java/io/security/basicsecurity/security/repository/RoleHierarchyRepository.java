package io.security.basicsecurity.security.repository;

import io.security.basicsecurity.security.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @since       2022.04.11
 * @author      minam
 * @description role hierarchy repository
 **********************************************************************************************************************/
public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
    RoleHierarchy findByChildName(String roleName);
}