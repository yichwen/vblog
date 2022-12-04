package io.blog.vblog.controller;

import io.blog.vblog.dto.LoginRequestDto;
import io.blog.vblog.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public R login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(loginRequestDto.toString());
        return R.ok();
    }

}
