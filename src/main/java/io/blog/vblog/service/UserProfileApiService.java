package io.blog.vblog.service;

import io.blog.vblog.dto.UserEmailUpdateDto;
import io.blog.vblog.dto.UserProfileDto;
import io.blog.vblog.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileApiService {

    @Autowired
    private UserService userService;

    public UserProfileDto getUserProfile() {
        User user = userService.getUser(getUserId());
        UserProfileDto userProfileDto = new UserProfileDto();
        BeanUtils.copyProperties(user, userProfileDto);
        return userProfileDto;
    }

    public void updateUserEmail(UserEmailUpdateDto userEmailUpdateDto) {
        User user = userService.getUser(getUserId());
        user.setEmail(userEmailUpdateDto.getEmail());
        userService.updateUser(user);
    }

    private Long getUserId() {
        return 6L;
    }

}
