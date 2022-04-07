package io.security.basicsecurity.security.common;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @since       2022.04.07
 * @author      minam
 * @description ajax login authentication entry point
 **********************************************************************************************************************/
public class AjaxLoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증받지 않은 상태로 접근하면 여기로 오게 되는데, 401로 보내버리겠다.
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UnAuthorized");
    }
}