package io.security.basicsecurity.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @since       2022.04.11
 * @author      minam
 * @description permit all filter
 **********************************************************************************************************************/
@Slf4j
public class PermitAllFilter extends FilterSecurityInterceptor {

    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private boolean observeOncePerRequest = true;

    private final List<RequestMatcher> matchers = new ArrayList<>();

    public PermitAllFilter(String... permitResources) {
        for(String resource : permitResources) {
            matchers.add(new AntPathRequestMatcher(resource));
        }
    }

    @Override
    protected InterceptorStatusToken beforeInvocation(Object object) {

        boolean permitAll = false;

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        for(RequestMatcher matcher : this.matchers) {
            if (matcher.matches(request)) {
                log.info("Requested in PermitAllFilter and permit all resource invoked");
                permitAll = true;
                break;
            }
        }

        if (permitAll) {
            return null; // 이렇게 하면 권한 심사를 하지 않게 된다.
        }

        return super.beforeInvocation(object);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        if (isApplied(filterInvocation) && this.observeOncePerRequest) {
            // filter already applied to this request and user wants us to observe
            // once-per-request handling, so don't re-do security checking
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }
        // first time this request being called, so perform security checking
        if (filterInvocation.getRequest() != null && this.observeOncePerRequest) {
            filterInvocation.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
        }
        InterceptorStatusToken token = beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        }
        finally {
            super.finallyInvocation(token);
        }
        super.afterInvocation(token, null);
    }

    private boolean isApplied(FilterInvocation filterInvocation) {
        return (filterInvocation.getRequest() != null)
                && (filterInvocation.getRequest().getAttribute(FILTER_APPLIED) != null);
    }
}