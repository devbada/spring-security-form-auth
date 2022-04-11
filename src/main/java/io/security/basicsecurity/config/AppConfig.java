package io.security.basicsecurity.config;

import io.security.basicsecurity.security.repository.AccessIpRepository;
import io.security.basicsecurity.security.repository.ResourcesRepository;
import io.security.basicsecurity.security.service.SecurityResourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @since       2022.04.08
 * @author      minam
 * @description app config
 **********************************************************************************************************************/
@Configuration
class AppConfig {

    @Bean
    public SecurityResourceService securityResourceService(ResourcesRepository resourcesRepository, AccessIpRepository accessIpRepository) {
        return new SecurityResourceService(resourcesRepository, accessIpRepository);
    }
}