package io.security.basicsecurity.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @since       2022.03.29
 * @author      minam
 * @description admin controller
 **********************************************************************************************************************/
@Controller
public class AdminController {

    @GetMapping("/admin/config")
    public String config() {
        return "/admin/config";
    }
}