package io.security.basicsecurity.security.config;

import io.security.basicsecurity.security.factory.MethodResourceFactoryBean;
import io.security.basicsecurity.security.factory.enumerate.ResourceType;
import io.security.basicsecurity.security.processor.ProtectPointcutPostProcessor;
import io.security.basicsecurity.security.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
        return new MethodResourceFactoryBean(securityResourceService, ResourceType.METHOD);
    }

    private MethodResourceFactoryBean pointcutResourceFactoryBean() {
        return new MethodResourceFactoryBean(securityResourceService, ResourceType.POINT_CUT);
    }


    /**
     *
     * 설정클래스에서 람다 형식으로 선언된 빈이 존재할 경우 오류가 발생하여 스프링 빈과 동일한 클래스를 생성하여 처리
     * 아직 AspectJ 라이브러리에서 Fix 하지 못한 것으로 판단되지만 다른 오류 원인이 존재하는지 계속 살펴보도록 함
     */
    @Bean
    @Profile("pointcut")
    public ProtectPointcutPostProcessor protectPointcutPostProcessor() {

        ProtectPointcutPostProcessor protectPointcutPostProcessor = new ProtectPointcutPostProcessor(methodSecurityMetadataSource());
        protectPointcutPostProcessor.setPointcutMap(pointcutResourceFactoryBean().getObject());

        return protectPointcutPostProcessor;
    }
}