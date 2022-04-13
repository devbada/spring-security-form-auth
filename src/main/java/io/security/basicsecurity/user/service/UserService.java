package io.security.basicsecurity.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 * @since       2022.04.13
 * @author      minam
 * @description user service
 **********************************************************************************************************************/
@Service
@Slf4j
public class UserService {


    @Secured("ROLE_MANAGER")
    public void order() {
        log.info("welcome manager, you entered the order()");
    }
}