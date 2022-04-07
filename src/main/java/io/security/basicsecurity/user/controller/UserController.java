package io.security.basicsecurity.user.controller;

import io.security.basicsecurity.admin.domain.dto.AccountDto;
import io.security.basicsecurity.admin.domain.entity.Account;
import io.security.basicsecurity.user.form.AccountForm;
import io.security.basicsecurity.user.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @since       2022.03.24
 * @author      minam
 * @description user controller
 **********************************************************************************************************************/
@Controller
@AllArgsConstructor
public class UserController {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

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

        ModelMapper modelMapper = new ModelMapper() ;
        Account account = modelMapper.map(form, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        Account persistAccount = accountService.add(account);

        model.addAttribute("account", persistAccount);

        return "redirect:/user/login";
    }
}