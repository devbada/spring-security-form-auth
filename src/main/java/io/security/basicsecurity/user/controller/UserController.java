package io.security.basicsecurity.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @since       2022.03.24
 * @author      minam
 * @description user controller
 **********************************************************************************************************************/
@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/myPage")
    public String myPage() {
        return "/user/myPage";
    }
}