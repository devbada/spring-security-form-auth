package io.security.basicsecurity.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @since       2022.04.07
 * @author      minam
 * @description message controller
 **********************************************************************************************************************/
@Controller
public class MessageController {

    @GetMapping("/messages")
    public String message() throws Exception {
        return "/user/messages";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public String apiMessages() {
        return "messages ok";
    }
}