package io.security.basicsecurity.user.repository;

import io.security.basicsecurity.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @since       2022.03.24
 * @author      minam
 * @description account repository
 **********************************************************************************************************************/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByName(String name);
}