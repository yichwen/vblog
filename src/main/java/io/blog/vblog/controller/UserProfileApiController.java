package io.blog.vblog.controller;

import io.blog.vblog.dto.UserEmailUpdateDto;
import io.blog.vblog.dto.UserProfileDto;
import io.blog.vblog.service.UserProfileApiService;
import io.blog.vblog.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileApiController {

    @Autowired
    private UserProfileApiService userProfileApiService;

    @GetMapping
    public R getUserProfile() {
        UserProfileDto userProfile = userProfileApiService.getUserProfile();
        return R.ok().data(userProfile);
    }
    
    @PutMapping("/email")
    public R updateUserEmail(@RequestBody UserEmailUpdateDto userEmailUpdateDto) {
        userProfileApiService.updateUserEmail(userEmailUpdateDto);
        return R.ok();
    }

}
