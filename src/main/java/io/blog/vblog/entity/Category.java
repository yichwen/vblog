package io.blog.vblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    private Date createdDateTime;

    @OneToMany(mappedBy = "category")
    private Collection<Article> articles;

}
