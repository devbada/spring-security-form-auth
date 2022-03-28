package io.security.basicsecurity.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @since       2022.03.28
 * @author      minam
 * @description custom authentication failure handler
 **********************************************************************************************************************/
@Component
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "Invalid Username or Password";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "E00100101"; // 인증 정보가 올바르지 않습니다.
        } else if (exception instanceof InsufficientAuthenticationException) {
            errorMessage = "E00100102"; // 인증 요청 정보 중 secretKey가 유효하지 않습니다.
        }
        log.error(errorMessage);

        setDefaultFailureUrl("/user/login?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
}