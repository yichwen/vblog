package io.blog.vblog.repository;

import io.blog.vblog.entity.UserRole;
import io.blog.vblog.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByIdUserId(Long userId);
}
