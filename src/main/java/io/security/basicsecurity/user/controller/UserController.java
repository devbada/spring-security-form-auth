package io.security.basicsecurity.user.controller;

import io.security.basicsecurity.admin.domain.dto.AccountDto;
import io.security.basicsecurity.user.adapter.AccountAdapter;
import io.security.basicsecurity.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @since       2022.03.24
 * @author      minam
 * @description user controller
 **********************************************************************************************************************/
@Controller
@AllArgsConstructor
public class UserController {

    private final AccountAdapter accountAdapter;
    private final UserService    userService;

    @GetMapping("/users/myPage")
    public String myPage() {
        return "/user/myPage";
    }

    @GetMapping("/users/register")
    public String join() {
        return "/user/login/register";
    }

    @SneakyThrows
    @PostMapping("/users")
    public String add(AccountDto.Request.Add form, Model model) {
        model.addAttribute("account", accountAdapter.createAccount(form));
        return "redirect:/user/login";
    }

    @GetMapping("/users/order")
    @ResponseBody
    public String order(Model model) {
        userService.order();
        return "ordered";
    }
}