package io.security.basicsecurity.security.common;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @since       2022.03.28
 * @author      minam
 * @description form web authentication details
 **********************************************************************************************************************/
public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String secretKey;

    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.secretKey = request.getParameter("secretKey");
    }

    public String getSecretKey() {
        return this.secretKey;
    }
}