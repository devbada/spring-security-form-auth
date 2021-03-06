package io.security.basicsecurity.security.config;

import io.security.basicsecurity.security.common.FormAuthenticationDetailsSource;
import io.security.basicsecurity.security.factory.UrlResourcesFactoryBean;
import io.security.basicsecurity.security.filter.PermitAllFilter;
import io.security.basicsecurity.security.handler.CustomAccessDeniedHandler;
import io.security.basicsecurity.security.handler.CustomAuthenticationFailureHandler;
import io.security.basicsecurity.security.handler.CustomAuthenticationSuccessHandler;
import io.security.basicsecurity.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import io.security.basicsecurity.security.provider.CustomAuthenticationProvider;
import io.security.basicsecurity.security.service.SecurityResourceService;
import io.security.basicsecurity.security.voter.IpAddressVoter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since       2022.01.05
 * @author      minam
 * @description security config
 **********************************************************************************************************************/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled=true, jsr250Enabled=true)
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormAuthenticationDetailsSource       authenticationDetailsSource;
    private final CustomAuthenticationSuccessHandler    successHandler;
    private final CustomAuthenticationFailureHandler    failureHandler;
    private final SecurityResourceService               securityResourceService;

    private final String[] permitAllResources = {"/", "/users/register", "/users", "/error", "/user/login*"};

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
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("userId")
                .loginPage("/user/login") // Controller ??? mapping url
                .loginProcessingUrl("/loginProcess")
                .defaultSuccessUrl("/")
                .authenticationDetailsSource(authenticationDetailsSource)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
            .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
            .and()
                .addFilterBefore(permitAllFilter(), FilterSecurityInterceptor.class)
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;
    }

    @Bean
    public Filter permitAllFilter() throws Exception {
        PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllResources);
        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        permitAllFilter.setAccessDecisionManager(affirmativeBased()); // ?????? ?????????
        permitAllFilter.setRejectPublicInvocations(false);
        return permitAllFilter;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler("/denied");
    }

    /*
    * js /css /image ?????? ??? ?????? ????????? ????????? ????????? ?????? ???????????? ??????
    **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                      .antMatchers("/webjars/**");
    }

    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {

        // ?????? ?????? UrlFilterInvocationSecurityMetadataSource??? ???????????? ?????? ??????
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased()); // ?????? ?????????
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean()); // ?????? ?????????
        return filterSecurityInterceptor;
    }

    @Bean
    public AccessDecisionManager affirmativeBased() {
        AffirmativeBased accessDecisionManager = new AffirmativeBased(getAccessDecisionVoters());
        accessDecisionManager.setAllowIfAllAbstainDecisions(false); // ?????? ?????? ?????? ????????? ?????? ????????? true ?????? ????????? false
        return accessDecisionManager;
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        AuthenticatedVoter authenticatedVoter = new AuthenticatedVoter();
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        IpAddressVoter ipAddressVoter = new IpAddressVoter(securityResourceService);

        return Arrays.asList(ipAddressVoter, authenticatedVoter, webExpressionVoter, roleVoter());
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        RoleHierarchyVoter voter = new RoleHierarchyVoter(roleHierarchy());
        voter.setRolePrefix("ROLE_");
        return voter;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        return new RoleHierarchyImpl();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlFilterInvocationSecurityMetadataSource(urlResourcesFactoryBean().getObject(), securityResourceService);
    }

    private UrlResourcesFactoryBean urlResourcesFactoryBean() {
        return new UrlResourcesFactoryBean(securityResourceService);
    }
}
