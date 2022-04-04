package io.security.basicsecurity.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.basicsecurity.security.token.AjaxAuthenticationToken;
import io.security.basicsecurity.user.form.AccountForm;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @since       2022.04.04
 * @author      minam
 * @description ajax authentication filter
 **********************************************************************************************************************/
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!isAjax(request)) {
            throw new IllegalAccessException("Authentication is not supported");
        }

        AccountForm.Request.Login login = objectMapper.readValue(request.getReader(), AccountForm.Request.Login.class);

        if (Objects.isNull(login)) {
            throw new IllegalArgumentException("Account information should not be null");
        }

        AjaxAuthenticationToken ajaxAuthenticationToken = new AjaxAuthenticationToken(login.getName(), login.getPassword());

        // AJAX용 인증 토큰 제공
        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}