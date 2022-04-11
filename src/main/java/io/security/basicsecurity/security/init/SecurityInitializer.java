package io.security.basicsecurity.security.init;

import io.security.basicsecurity.security.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.stereotype.Component;

/**
 * @since       2022.04.11
 * @author      minam
 * @description security initializer
 **********************************************************************************************************************/
@Component
@RequiredArgsConstructor
public class SecurityInitializer implements ApplicationRunner {

    private final RoleHierarchyService roleHierarchyService;
    private final RoleHierarchyImpl    roleHierarchy;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleHierarchy.setHierarchy(roleHierarchyService.findAllHierarchy());
    }
}