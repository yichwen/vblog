package io.blog.vblog.controller.admin;

import io.blog.vblog.dto.admin.RoleDto;
import io.blog.vblog.service.RoleService;
import io.blog.vblog.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/roles")
public class RoleAdminController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public R getRoles() {
        List<RoleDto> roles = roleService.getRoles();
        return R.ok().data(roles);
    }

}
