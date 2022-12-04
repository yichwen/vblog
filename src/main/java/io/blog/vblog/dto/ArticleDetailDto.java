package io.blog.vblog.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ArticleDetailDto {
    private Long id;
    private String title;
    private String htmlContent;
    private String mdContent;
    private String authorNickname;
    private Long categoryId;
    private Long viewCount;
    private Date editDateTime;
    private List<Object> tags = new ArrayList<>();
}
