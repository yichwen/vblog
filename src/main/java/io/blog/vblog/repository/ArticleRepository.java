package io.blog.vblog.repository;

import io.blog.vblog.entity.Article;
import io.blog.vblog.entity.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByAuthorId(Pageable pageable, Long authorId);
    Page<Article> findByAuthorIdAndStatus(Pageable pageable, Long authorId, ArticleStatus status);
    Optional<Article> findByIdAndAuthorId(Long id, Long authorId);
}
