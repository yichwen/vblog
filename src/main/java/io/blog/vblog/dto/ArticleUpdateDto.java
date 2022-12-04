package io.blog.vblog.dto;

import lombok.Data;

@Data
public class ArticleUpdateDto {
    private String title;
    private String mdContent;
    private String htmlContent;
    private String summary;
    private Long categoryId;
}
