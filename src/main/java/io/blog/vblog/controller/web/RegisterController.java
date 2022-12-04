package io.blog.vblog.controller.web;

import io.blog.vblog.dto.UserCreationDto;
import io.blog.vblog.service.UserService;
import io.blog.vblog.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/v1/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public R register(UserCreationDto userCreationDto) {
        userService.createUser(userCreationDto);
        return R.ok();
    }

}
