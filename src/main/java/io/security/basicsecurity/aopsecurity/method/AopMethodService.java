package io.security.basicsecurity.aopsecurity.method;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @since       2022.04.13
 * @author      minam
 * @description aop method service
 **********************************************************************************************************************/

@Service
@Slf4j
public class AopMethodService {

    public void methodSecured() {
        log.info("welcome AopMethodService methodSecured");
    }
}