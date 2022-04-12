package io.security.basicsecurity.user.service;

import io.security.basicsecurity.admin.domain.entity.Account;
import io.security.basicsecurity.common.exception.NotFoundException;
import io.security.basicsecurity.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.List;
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

    @SneakyThrows
    @Transactional(readOnly=true)
    public Account getByName(final String username) {
        return accountRepository.findByName(username).orElse(null);
    }

    @SneakyThrows
    public Account add(Account account) {

        if (Objects.nonNull(getByName(account.getName()))) {
            throw new Exception("already Exists");
        }

        return accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account get(Long accountId) throws NotFoundException {
        return accountRepository.findById(accountId).orElseThrow(NotFoundException::new);
    }
}