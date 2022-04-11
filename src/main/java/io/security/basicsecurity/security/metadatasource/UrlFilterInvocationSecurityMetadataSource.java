package io.security.basicsecurity.security.metadatasource;

import io.security.basicsecurity.security.service.SecurityResourceService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @since       2022.04.08
 * @author      minam
 * @description url filter invocation security metadata source
 **********************************************************************************************************************/

@RequiredArgsConstructor
@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;

    private final SecurityResourceService securityResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        /**
         * Object object = filter 에서 invoked 된 클래스가 들어오게 됨
         */
        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        if (requestMap != null) {
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : this.requestMap.entrySet()) {
                if (entry.getKey().matches(request)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void reload() {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedResources = securityResourceService.getResourceList();
        Iterator<Map.Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedResources.entrySet().iterator();

        this.requestMap.clear();

        while (iterator.hasNext()) {
            Map.Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
            this.requestMap.put(entry.getKey(), entry.getValue());
        }
    }
}