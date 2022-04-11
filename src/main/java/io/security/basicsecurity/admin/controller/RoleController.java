package io.security.basicsecurity.admin.controller;

import io.security.basicsecurity.admin.domain.dto.RoleDto;
import io.security.basicsecurity.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @since       2022.04.07
 * @author      minam
 * @description role controller
 **********************************************************************************************************************/
@Controller
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/admin/roles")
    public String roles(Model model) {
        List<RoleDto.Response.FindAll> roles = roleService.findAll().stream()
                                                                     .map(role -> modelMapper.map(role, RoleDto.Response.FindAll.class))
                                                                     .collect(Collectors.toList());
        model.addAttribute("roles", roles);
        return "/admin/role/list";
    }

    @GetMapping("/admin/roles/{id}")
    public String roles(@PathVariable Long id, Model model) {
        RoleDto.Response.FindOne role = modelMapper.map(roleService.findById(id), RoleDto.Response.FindOne.class);
        model.addAttribute("role", role);
        return "/admin/role/detail";
    }
}