package io.security.basicsecurity.security.context;

import io.security.basicsecurity.user.entity.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @since       2022.03.25
 * @author      minam
 * @description account context
 **********************************************************************************************************************/

@Getter
public class AccountContext extends User {

    private final Account account;

    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getName(), account.getPassword(), authorities);
        this.account = account;
    }
}