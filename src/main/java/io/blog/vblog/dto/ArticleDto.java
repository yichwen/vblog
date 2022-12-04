package io.blog.vblog.dto;

import io.blog.vblog.entity.ArticleStatus;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDto {
    private Long id;
    private String title;
//    private Long categoryId;
    private String categoryName;
    private String authorNickname;

    private ArticleStatus status;
    private Date publishDateTime;
    private Date editDateTime;
    private Long viewCount;
}
