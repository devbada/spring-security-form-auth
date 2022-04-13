package io.security.basicsecurity.security.config;

import io.security.basicsecurity.security.factory.MethodResourceFactoryBean;
import io.security.basicsecurity.security.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import java.util.Objects;

/**
 * @since       2022.04.13
 * @author      minam
 * @description method security config
 **********************************************************************************************************************/
@Configuration
@EnableGlobalMethodSecurity
@RequiredArgsConstructor
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    private final SecurityResourceService securityResourceService;
    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new MapBasedMethodSecurityMetadataSource(); // 맵기반으로 처리할 수 있도록
    }

    @Bean
    public MapBasedMethodSecurityMetadataSource methodSecurityMetadataSource() {
        return new MapBasedMethodSecurityMetadataSource(Objects.requireNonNull(methodResourceFactoryBean().getObject()));
    }

    private MethodResourceFactoryBean methodResourceFactoryBean() {
        return new MethodResourceFactoryBean(securityResourceService);
    }
}