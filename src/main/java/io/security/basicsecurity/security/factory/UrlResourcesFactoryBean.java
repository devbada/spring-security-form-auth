package io.security.basicsecurity.security.factory;

import io.security.basicsecurity.security.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @since       2022.04.08
 * @author      minam
 * @description url resources factory bean
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class UrlResourcesFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    // DB로부터 자원/권한 정보를 호출하여 갖고 있다.
    private final SecurityResourceService                        securityResourceService;
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
        // resourceMap
        if (CollectionUtils.isEmpty(resourceMap)) {
            init();
        }
        return resourceMap;
    }

    private void init() {
        this.resourceMap = securityResourceService.getResourceList();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}