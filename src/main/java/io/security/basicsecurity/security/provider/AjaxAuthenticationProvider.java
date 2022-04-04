package io.security.basicsecurity.security.provider;

import io.security.basicsecurity.security.context.AccountContext;
import io.security.basicsecurity.security.token.AjaxAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @since       2022.04.04
 * @author      minam
 * @description ajax authentication provider
 **********************************************************************************************************************/
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 검증을 위한 구현체
     * @return AjaxAuthenticationToken
     * @throws AuthenticationException, BadCredentialsException
     */
    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(authentication.getName());

        if (!passwordEncoder.matches((String) authentication.getCredentials(), accountContext.getPassword())) {
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 정책에 따라 추가 검증을 할 수 있다.
        return new AjaxAuthenticationToken( accountContext.getAccount()
                                           ,null
                                           ,accountContext.getAuthorities());
    }

    /**
     * 인증 처리
     * @return boolean
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}