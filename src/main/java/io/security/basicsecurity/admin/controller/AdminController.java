package io.security.basicsecurity.admin.controller;

import io.security.basicsecurity.admin.domain.dto.AccountDto;
import io.security.basicsecurity.user.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @since       2022.03.29
 * @author      minam
 * @description admin controller
 **********************************************************************************************************************/
@Controller
@AllArgsConstructor
public class AdminController {

    private final AccountService accountService;
    private final ModelMapper modelMapper = new ModelMapper() ;

    @GetMapping("/admin")
    public String home() {
        return "/admin/home";
    }

    @GetMapping("/admin/config")
    public String config() {
        return "/admin/config";
    }

    @GetMapping("/admin/accounts")
    public String accounts(Model model) {

        List<AccountDto.Response.FindAll> list = accountService.findAll()
                .stream()
                .map(account -> modelMapper.map(account, AccountDto.Response.FindAll.class))
                .collect(Collectors.toList());
        model.addAttribute("accounts", list);
        return "/admin/user/list";
    }

    @GetMapping("/admin/accounts/{accountId}")
    public String accounts(@PathVariable("accountId") Long accountId, Model model) {

        AccountDto.Response.FindOne account = modelMapper.map(accountService.get(accountId), AccountDto.Response.FindOne.class);
        model.addAttribute("account", account);
        return "/admin/user/detail";
    }
}