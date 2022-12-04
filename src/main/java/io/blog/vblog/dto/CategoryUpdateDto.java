package io.blog.vblog.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryUpdateDto {
    @NotNull
    private String name;
}
