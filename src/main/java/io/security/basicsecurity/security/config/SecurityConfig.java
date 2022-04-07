package io.security.basicsecurity.security.config;

import io.security.basicsecurity.security.common.FormAuthenticationDetailsSource;
import io.security.basicsecurity.security.handler.CustomAccessDeniedHandler;
import io.security.basicsecurity.security.handler.CustomAuthenticationFailureHandler;
import io.security.basicsecurity.security.handler.CustomAuthenticationSuccessHandler;
import io.security.basicsecurity.security.provider.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @since       2022.01.05
 * @author      minam
 * @description security config
 **********************************************************************************************************************/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormAuthenticationDetailsSource       authenticationDetailsSource;
    private final CustomAuthenticationSuccessHandler    successHandler;
    private final CustomAuthenticationFailureHandler    failureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/myPage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/users/register", "/users", "/error", "/user/login*").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("userId")
                .loginPage("/user/login") // Controller 의 mapping url
                .loginProcessingUrl("/loginProcess")
                .defaultSuccessUrl("/")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
        ;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler("/denied");
    }

    /*
    * js /css /image 파일 등 보안 필터를 적용할 필요가 없는 리소스를 설정
    **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                      .antMatchers("/webjars/**");
    }
}
