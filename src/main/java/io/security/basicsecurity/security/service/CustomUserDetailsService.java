package io.security.basicsecurity.security.service;

import io.security.basicsecurity.security.context.AccountContext;
import io.security.basicsecurity.user.entity.Account;
import io.security.basicsecurity.user.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @since       2022.03.25
 * @author      minam
 * @description custom user details service
 **********************************************************************************************************************/
@Service("userDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getByName(username);


        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(account.getRole()));

        return new AccountContext(account, roles);
    }
}