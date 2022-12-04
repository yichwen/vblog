package io.blog.vblog.dto;

import lombok.Data;

@Data
public class UserCreationDto {
    private String username;
    private String nickname;
    private String password;
    private String email;
}
