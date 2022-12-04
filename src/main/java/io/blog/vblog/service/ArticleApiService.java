package io.blog.vblog.service;

import io.blog.vblog.dto.ArticleCreationDto;
import io.blog.vblog.dto.ArticleDetailDto;
import io.blog.vblog.dto.ArticleDto;
import io.blog.vblog.entity.Article;
import io.blog.vblog.entity.ArticleStatus;
import io.blog.vblog.entity.User;
import io.blog.vblog.repository.ArticleRepository;
import io.blog.vblog.util.P;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ArticleApiService {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryApiService categoryApiService;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public P<ArticleDto> getArticles(Pageable pageable, String status) {
        ArticleStatus articleStatus = ArticleStatus.from(status);
        Long authorId = 6L;
        Page<Article> articles = null;
        if (articleStatus == null) {
            articles = articleRepository.findByAuthorId(pageable, authorId);
        } else {
            articles = articleRepository.findByAuthorIdAndStatus(pageable, authorId, articleStatus);
        }
        return P.getInstance(articles, article -> {
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(article, articleDto);
            articleDto.setAuthorNickname(article.getAuthor().getNickname());
            articleDto.setCategoryName(article.getCategory().getName());
            return articleDto;
        });
    }

    @Transactional
    public ArticleDetailDto getArticle(Long id) {
        Long authorId = 6L;
        return articleRepository.findByIdAndAuthorId(id, authorId)
                .map(article -> {
                    ArticleDetailDto articleDto = new ArticleDetailDto();
                    BeanUtils.copyProperties(article, articleDto);
                    articleDto.setAuthorNickname(article.getAuthor().getNickname());
                    articleDto.setCategoryId(article.getCategory().getId());
                    return articleDto;
                })
                .orElseThrow(() -> {
                    String message = String.format("article with id [%d] is not found", id);
                    throw new IllegalStateException(message);
                });
    }

    public void createArticle(ArticleCreationDto articleCreationDto) {
        Long id = articleCreationDto.getId();
        if (id != null && id > 0) {
            updateArticle(id, articleCreationDto);
            return;
        }
        Article article = new Article();
        BeanUtils.copyProperties(articleCreationDto, article);
        article.setAuthor(getUser());
        article.setCategory(categoryApiService.getCategory(articleCreationDto.getCategoryId()));
        articleRepository.save(article);
    }

    public void updateArticle(Long id, ArticleCreationDto articleCreationDto) {
        Article article = getArticleByIdAndAuthorId(id, getUserId());
//        articleCreationDto.getCategoryId();
        article.setHtmlContent(articleCreationDto.getHtmlContent());
        article.setMdContent(articleCreationDto.getMdContent());
        article.setSummary(articleCreationDto.getSummary());
        article.setTitle(articleCreationDto.getTitle());
        article.setStatus(articleCreationDto.getStatus());
        if (article.getStatus() == ArticleStatus.PUBLISHED) {
            article.setPublishDateTime(new Date());
        }
        article.setEditDateTime(new Date());
        articleRepository.save(article);
    }

    private Article getArticleByIdAndAuthorId(Long id, Long authorId) {
        return articleRepository.findByIdAndAuthorId(id, authorId)
                .orElseThrow(() -> {
                    String message = String.format("article with id [%d] is not found under author id [%d]", id, authorId);
                    throw new IllegalStateException(message);
                });
    }

    private Long getUserId() {
        return 6L;
    }

    private User getUser() {
        return userService.getUser(getUserId());
    }

    public void deleteArticle(Long id) {
        Article article = getArticleByIdAndAuthorId(id, getUserId());
        if (article.getStatus() == ArticleStatus.DELETED) {
            articleRepository.delete(article);
        } else {
            article.setStatus(ArticleStatus.DELETED);
            articleRepository.save(article);
        }
    }

}
