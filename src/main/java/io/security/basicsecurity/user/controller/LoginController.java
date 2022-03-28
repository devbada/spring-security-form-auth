package io.security.basicsecurity.user.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @since       2022.03.28
 * @author      minam
 * @description login controller
 **********************************************************************************************************************/
@Controller
@AllArgsConstructor
public class LoginController {

    @GetMapping("/user/login")
    public String myPage() {
        return "/user/login/login";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/user/login";
    }
}