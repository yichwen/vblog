package io.blog.vblog.dto;

import io.blog.vblog.entity.ArticleStatus;
import lombok.Data;

@Data
public class ArticleCreationDto {
    private Long id;
    private String title;
    private String mdContent;
    private String htmlContent;
    private String summary;
    private Long categoryId;
    private ArticleStatus status;
}
