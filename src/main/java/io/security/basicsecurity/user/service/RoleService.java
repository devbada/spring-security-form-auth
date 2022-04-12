package io.security.basicsecurity.user.service;

import io.security.basicsecurity.common.exception.NotFoundException;
import io.security.basicsecurity.security.entity.Role;
import io.security.basicsecurity.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @since       2022.04.08
 * @author      minam
 * @description role service
 **********************************************************************************************************************/
@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly=true)
    public Set<Role> gets(List<String> names) {
        return roleRepository.findByRoleNameIn(names);
    }

    @Transactional(readOnly=true)
    public Role findByRoleName(String name) {
        return roleRepository.findByRoleName(name).orElseThrow(NotFoundException::new);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}