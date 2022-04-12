package io.security.basicsecurity.security.voter;

import io.security.basicsecurity.security.service.SecurityResourceService;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.List;

/**
 * @since       2022.04.11
 * @author      minam
 * @description ip address voter
 **********************************************************************************************************************/
public class IpAddressVoter implements AccessDecisionVoter<Object> {

    private final SecurityResourceService securityResourceService;

    public IpAddressVoter(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return (attribute.getAttribute() != null);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     *
     * @param authentication
     * @param object (request 정보가 있다)
     * @param attributes
     * @return
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

        if (!(authentication.getDetails() instanceof WebAuthenticationDetails)) {
            return ACCESS_DENIED;
        }

        // 심의 로직 구현
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        String remoteAddress = details.getRemoteAddress();

        // DB에 저장된 정보와 요청자의 IP 정보를 비교하는 로직이 필요.
        List<String> accessIpList = securityResourceService.getAccessIpList();

        int result = ACCESS_DENIED;

        for (String ipAddress : accessIpList) {
            if (remoteAddress.equals(ipAddress)) {
                result = ACCESS_ABSTAIN;
                break;
            }
        }

        if (result == ACCESS_DENIED) {
            throw new AccessDeniedException("Not allowed ip address");
        }

        return result;
    }
}