package io.blog.vblog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private Boolean enabled;

    private String email;

    private String profilePicURL;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date registrationDateTime;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Collection<Article> articles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Collection<UserRole> userRoles;

}
