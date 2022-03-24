package io.security.basicsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @since       2022.01.05
 * @author      minam
 * @description security controller
 **********************************************************************************************************************/
@RestController
public class SecurityController {

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String user(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext sessionAttribute = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        System.out.println(sessionAttribute.getAuthentication().getName());
        // 위의 authentication & sessionAttribute의 authentication 은 동일한 객체다

        return authentication.getName();
    }

    @GetMapping("/thread")
    public String thread() {

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        System.out.println("authentication name in thread [" + authentication.getName() +"]");
                        // 기본 모드에서는 securityContext를 공유할 수 없다.
                    }
                }
        ).start();

        return "thread";
    }

    @GetMapping("/admin/pay")
    public String pay() {
        return "admin pay";
    }

    @GetMapping("/admin/users")
    public String users() {
        return "admin users";
    }

    @GetMapping("/denied")
    public String denied() {
        return "access denied";
    }

    @PostMapping("/join/users")
    public String userAdd(Map<String, Object> userInfo) {
        return "ok";
    }
}