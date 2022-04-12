package io.security.basicsecurity.user.repository;

import io.security.basicsecurity.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @since       2022.03.24
 * @author      minam
 * @description account repository
 **********************************************************************************************************************/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String name);

    Set<Role> findByRoleNameIn(List<String> name);
}