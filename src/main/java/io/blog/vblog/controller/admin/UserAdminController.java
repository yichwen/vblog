package io.blog.vblog.controller.admin;

import io.blog.vblog.dto.admin.UserDto;
import io.blog.vblog.dto.admin.UserRoleUpdateDto;
import io.blog.vblog.service.UserService;
import io.blog.vblog.util.P;
import io.blog.vblog.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/users")
public class UserAdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public R getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                      @RequestParam(value = "size", defaultValue = "10") int size) {
        P<UserDto> users = userService.getUsers(PageRequest.of(page, size));
        return R.ok().data(users);
    }

    @GetMapping("/{id}")
    public R getUserById(@PathVariable("id") Long id) {
        UserDto user = userService.getUserById(id);
        return R.ok().data(user);
    }

    @PutMapping("/{id}/enable")
    public R toggleUserEnabled(@PathVariable("id") Long id) {
        userService.toggleUserEnabled(id);
        return R.ok();
    }

    @PutMapping("/{id}/roles")
    public R updateUserRoles(@PathVariable("id") Long id, @RequestBody UserRoleUpdateDto userRoleUpdateDto) {
        userService.updateUserRoles(id, userRoleUpdateDto.getRoleIds());
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return R.ok();
    }

}
