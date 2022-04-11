package io.security.basicsecurity.security.service;

import io.security.basicsecurity.security.entity.RoleHierarchy;
import io.security.basicsecurity.security.repository.RoleHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * @since       2022.04.11
 * @author      minam
 * @description role hierarchy service
 **********************************************************************************************************************/
@Service
@RequiredArgsConstructor
public class RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Transactional
    public String findAllHierarchy() {

        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        Iterator<RoleHierarchy> iterator = roleHierarchies.iterator();
        StringBuilder concatRoles = new StringBuilder();

        while (iterator.hasNext()) {
            RoleHierarchy roleHierarchy = iterator.next();
            if (roleHierarchy.getParentName() != null) {
                concatRoles.append(roleHierarchy.getParentName().getChildName());
                concatRoles.append(" > ");
                concatRoles.append(roleHierarchy.getChildName());
                concatRoles.append("\n");
            }
        }
        return concatRoles.toString();
    }
}