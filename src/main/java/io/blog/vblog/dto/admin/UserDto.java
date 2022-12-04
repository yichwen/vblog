package io.blog.vblog.dto.admin;

import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String nickname;
    private Boolean enabled;
    private String email;
    private String profilePicURL;
    private Date registrationDateTime;
    private Collection<RoleDto> roles;
}
