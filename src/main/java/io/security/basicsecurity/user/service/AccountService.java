package io.security.basicsecurity.user.service;

import io.security.basicsecurity.user.entity.Account;
import io.security.basicsecurity.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @since       2022.03.24
 * @author      minam
 * @description account service impl
 **********************************************************************************************************************/
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account getByName(final String name) {
        return accountRepository.findByName(name);
    }

    @SneakyThrows
    public Account add(Account account) {

        if (Objects.nonNull(getByName(account.getName()))) {
            throw new Exception("already Exists");
        }
        return accountRepository.save(account);
    }
}