package io.security.basicsecurity.security.provider;

import io.security.basicsecurity.security.context.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @since       2022.03.25
 * @author      minam
 * @description custom authentication provider
 **********************************************************************************************************************/
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder    passwordEncoder;

    /**
     * 검증을 위한 구현체
     * @return UsernamePasswordAuthenticationToken
     * @throws AuthenticationException, BadCredentialsException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(name);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 정책에 따라 추가 검증을 할 수 있다.
        return new UsernamePasswordAuthenticationToken( accountContext.getAccount()
                                                       ,accountContext.getPassword()
                                                       ,accountContext.getAuthorities());
    }

    /**
     * 인증 처리
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}