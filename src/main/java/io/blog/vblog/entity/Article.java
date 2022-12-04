package io.blog.vblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "mdContent", columnDefinition = "TEXT")
    private String mdContent;

    @Column(name = "htmlContent", columnDefinition = "TEXT")
    private String htmlContent;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;

    @Column(name = "status")
    private ArticleStatus status;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "publishDateTime")
    private Date publishDateTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "editDateTime")
    private Date editDateTime;

    @Column(name = "viewCount")
    private Long viewCount;

}
