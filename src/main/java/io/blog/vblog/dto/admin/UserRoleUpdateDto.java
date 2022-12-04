package io.blog.vblog.dto.admin;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleUpdateDto {
    private List<Long> roleIds;
}
