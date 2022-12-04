package io.blog.vblog.service;

import io.blog.vblog.dto.admin.RoleDto;
import io.blog.vblog.entity.Role;
import io.blog.vblog.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private static final String DEFAULT_USER_ROLE = "Normal User";

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream().map(role -> {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            return roleDto;
        }).collect(Collectors.toList());
    }

    public Role getDefaultUserRole() {
        try {
            return getRoleByName(DEFAULT_USER_ROLE);
        } catch (IllegalStateException ex) {
            return createRole(DEFAULT_USER_ROLE);
        }
    }

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> {
            String message = String.format("role with id [%d] is not found", id);
            throw new IllegalStateException(message);
        });
    }

    private Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    private Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> {
            String message = String.format("role with name [%s] is not found", name);
            throw new IllegalStateException(message);
        });
    }

}
