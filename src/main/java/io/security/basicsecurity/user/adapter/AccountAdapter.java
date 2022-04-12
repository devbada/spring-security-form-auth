package io.security.basicsecurity.user.adapter;

import io.security.basicsecurity.admin.domain.dto.AccountDto;
import io.security.basicsecurity.admin.domain.entity.Account;
import io.security.basicsecurity.security.entity.Role;
import io.security.basicsecurity.user.service.AccountService;
import io.security.basicsecurity.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @since       2022.04.08
 * @author      minam
 * @description account adapter
 **********************************************************************************************************************/
@Component
@RequiredArgsConstructor
@Transactional
public class AccountAdapter {

    private final AccountService    accountService;
    private final RoleService       roleService;
    private final PasswordEncoder   passwordEncoder;
    private final ModelMapper       modelMapper = new ModelMapper() ;

    public Account createAccount(AccountDto.Request.Add form) {

        Account account = modelMapper.map(form, Account.class);

        Set<Role> roles = Optional.ofNullable(roleService.gets(form.getRoles())).orElse(new HashSet<>());
        account.setRoles(roles);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountService.add(account);
    }
}