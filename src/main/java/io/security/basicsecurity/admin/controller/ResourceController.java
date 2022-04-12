package io.security.basicsecurity.admin.controller;

import io.security.basicsecurity.admin.domain.dto.ResourceDto;
import io.security.basicsecurity.admin.domain.dto.RoleDto;
import io.security.basicsecurity.admin.service.ResourcesService;
import io.security.basicsecurity.security.entity.Resources;
import io.security.basicsecurity.security.entity.Role;
import io.security.basicsecurity.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import io.security.basicsecurity.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @since       2022.04.07
 * @author      minam
 * @description resource controller
 **********************************************************************************************************************/
@Controller
@RequiredArgsConstructor
public class ResourceController {

    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    private final RoleService      roleService;
    private final ResourcesService resourcesService;
    private final ModelMapper      modelMapper = new ModelMapper();

    @GetMapping("/admin/resources")
    public String getResources(Model model) throws Exception {
        List<ResourceDto.Response.FindAll> resources = resourcesService.selectResources().stream()
                                                                                         .map(resource -> modelMapper.map(resource, ResourceDto.Response.FindAll.class))
                                                                                         .collect(Collectors.toList());
        model.addAttribute("resources", resources);
        return "/admin/resource/list";
    }

    @GetMapping("/admin/resources/{id}")
    public String getResource(@PathVariable Long id, Model model) throws Exception {
        ResourceDto.Response.FindOne resource = modelMapper.map(resourcesService.selectResources(id), ResourceDto.Response.FindOne.class);

        List<RoleDto.Response.FindAll> roles = roleService.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDto.Response.FindAll.class))
                .collect(Collectors.toList());

        model.addAttribute("resources", resource);
        model.addAttribute("roleList", roles);
        return "/admin/resource/detail";
    }

    @GetMapping("/admin/resources/register")
    public String register(Model model) throws Exception {
        List<RoleDto.Response.FindAll> roles = roleService.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDto.Response.FindAll.class))
                .collect(Collectors.toList());

        model.addAttribute("roleList", roles);
        return "/admin/resource/register";
    }

    @PostMapping("/admin/resources")
    public String createResource(ResourceDto.Request.Add resourceDto) throws Exception {
        Set<Role> roles =  roleService.gets(resourceDto.getRoleName());

        Resources resources = modelMapper.map(resourceDto, Resources.class);
        resources.setRoleSet(roles);

        resourcesService.insertResources(resources);

        urlFilterInvocationSecurityMetadataSource.reload(); // 인가정보 갱신

        return "redirect:/admin/resources";
    }

    @DeleteMapping("/admin/resources/delete/{id}")
    public String deleteResource(@PathVariable Long id, Model model) {
        Resources resources = resourcesService.selectResources(id);
        resourcesService.deleteResources(resources);
        urlFilterInvocationSecurityMetadataSource.reload();

        return "redirect:/admin/resources";
    }
}