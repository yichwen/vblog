package io.blog.vblog.dto;

import lombok.Data;

@Data
public class UserProfileDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String profilePicURL;
    private String registrationDateTime;
}
