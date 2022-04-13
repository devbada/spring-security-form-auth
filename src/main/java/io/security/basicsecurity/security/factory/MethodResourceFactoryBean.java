package io.security.basicsecurity.security.factory;

import io.security.basicsecurity.security.factory.enumerate.ResourceType;
import io.security.basicsecurity.security.service.SecurityResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @since       2022.04.13
 * @author      minam
 * @description method resource factory bean
 **********************************************************************************************************************/
@RequiredArgsConstructor
public class MethodResourceFactoryBean  implements FactoryBean<LinkedHashMap<String, List<ConfigAttribute>>> {

    // DB로부터 자원/권한 정보를 호출하여 갖고 있다.
    private final SecurityResourceService securityResourceService;
    private LinkedHashMap<String, List<ConfigAttribute>> resourceMap;
    private final ResourceType resourceType;

    @Override
    public LinkedHashMap<String, List<ConfigAttribute>> getObject() {
        // resourceMap
        if (CollectionUtils.isEmpty(resourceMap)) {
            init();
        }
        return resourceMap;
    }

    private void init() {
        switch (resourceType) {
            case METHOD: {
                this.resourceMap = securityResourceService.getMethodResourceList();
                break;
            }
            case POINT_CUT:
                this.resourceMap = securityResourceService.getPointcutResourceList();
            default: break;
        }
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

}