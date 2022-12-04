package io.blog.vblog.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    // @JoinColumn overrides @Column in UserRoleId ?
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private User user;

}
