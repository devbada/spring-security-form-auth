package io.security.basicsecurity.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @since       2022.03.25
 * @author      minam
 * @description base controller
 **********************************************************************************************************************/
@Controller
public class BaseController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }
}