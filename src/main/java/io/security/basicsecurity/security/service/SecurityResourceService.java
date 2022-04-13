package io.security.basicsecurity.security.service;

import io.security.basicsecurity.security.entity.AccessIp;
import io.security.basicsecurity.security.entity.Resources;
import io.security.basicsecurity.security.repository.AccessIpRepository;
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
import java.util.stream.Collectors;

/**
 * @since       2022.04.08
 * @author      minam
 * @description security resource service
 **********************************************************************************************************************/
@Service
@RequiredArgsConstructor
public class SecurityResourceService {

    private final ResourcesRepository resourcesRepository;
    private final AccessIpRepository accessIpRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllResources();

        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getRoleSet().forEach(role -> {
                configAttributes.add(new SecurityConfig(role.getRoleName())); // 권한 정보
            });
            resourceMap.put(new AntPathRequestMatcher(resource.getResourceName()), configAttributes);
        });

        return resourceMap;
    }

    public List<String> getAccessIpList() {
        return accessIpRepository.findAll().stream().map(AccessIp::getIpAddress).collect(Collectors.toList());
    }

    public LinkedHashMap<String, List<ConfigAttribute>> getMethodResourceList() {
        LinkedHashMap<String, List<ConfigAttribute>> resourceMap = new LinkedHashMap<>();
        List<Resources> resources = resourcesRepository.findAllMethodResources();

        resources.forEach(resource -> {
            List<ConfigAttribute> configAttributes = new ArrayList<>();
            resource.getRoleSet().forEach(role -> {
                configAttributes.add(new SecurityConfig(role.getRoleName())); // 권한 정보
            });
            resourceMap.put(resource.getResourceName(), configAttributes);
        });

        return resourceMap;
    }
}