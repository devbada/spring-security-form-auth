package io.security.basicsecurity.security.service;

import io.security.basicsecurity.security.entity.Resources;
import io.security.basicsecurity.security.repository.ResourcesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @since       2022.04.08
 * @author      minam
 * @description security resource service
 **********************************************************************************************************************/
@Service
@RequiredArgsConstructor
public class SecurityResourceService {

    private final ResourcesRepository resourcesRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllResources();

        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getRoleSet().forEach(role -> {
                configAttributes.add(new SecurityConfig(role.getRoleName())); // 권한 정보
                resourceMap.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributes);
            });
        });

        return resourceMap;
    }
}