package io.security.basicsecurity.aopsecurity.controller;

import io.security.basicsecurity.admin.domain.dto.AccountDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @since       2022.04.12
 * @author      minam
 * @description aop security controller
 **********************************************************************************************************************/
@Controller
public class AopSecurityController {

    @GetMapping("/preAuthorize")
    @PreAuthorize("hasRole('ROLE_USER') and #accountDto.name == principal.name")
    public String preAuthorize(AccountDto.Request.Find accountDto, Model model, Principal principal) {
        model.addAttribute("method", "Success @PreAuthorize");
        return "/aop/method";
    }
}