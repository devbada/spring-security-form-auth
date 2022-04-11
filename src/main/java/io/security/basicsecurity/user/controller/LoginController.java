package io.security.basicsecurity.user.controller;

import io.security.basicsecurity.admin.domain.entity.Account;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/login")
    public String goLogin() {
        return "redirect:/user/login";
    }

    @GetMapping("/user/login")
    public String login(
         @RequestParam(value="error", required=false) String error
        ,@RequestParam(value="exception", required=false) String exception
        ,Model model) {

        model.addAttribute("error", error);

        if (StringUtils.isNotBlank(exception)) {
            String exceptionMessage = "";
            switch (exception) {
                case "E00100101":
                    exceptionMessage = "인증 정보가 올바르지 않습니다.";
                    break;
                case "E00100102":
                    exceptionMessage = "인증 요청 정보 중 secretKey가 유효하지 않습니다.";
                    break;
                default:
                    break;
            }
            model.addAttribute("exception", exceptionMessage);
        } else {
            model.addAttribute("exception", exception);
        }


        return "/user/login/login";
    }

    @GetMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();

        if (Objects.nonNull(authentication)){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/user/login";
    }

    @GetMapping("/denied")
    public String denied(@RequestParam(value="exception", required=false) String exception, Model model ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Account account = (Account) authentication.getPrincipal();
        model.addAttribute("name", account.getName());
        model.addAttribute("exception", exception);

        return "/user/login/denied";
    }
}