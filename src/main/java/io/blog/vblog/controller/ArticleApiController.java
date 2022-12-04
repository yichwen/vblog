package io.blog.vblog.controller;

import io.blog.vblog.dto.ArticleCreationDto;
import io.blog.vblog.dto.ArticleDetailDto;
import io.blog.vblog.dto.ArticleDto;
import io.blog.vblog.service.ArticleApiService;
import io.blog.vblog.util.P;
import io.blog.vblog.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleApiController {

    @Autowired
    private ArticleApiService articleApiService;

    @GetMapping
    public R getArticles(@RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         @RequestParam(value = "status", required = false) String status) {
        P<ArticleDto> articles = articleApiService.getArticles(PageRequest.of(page, size), status);
        return R.ok().data(articles);
    }

    @GetMapping("/{id}")
    public R getArticle(@PathVariable("id") Long id) {
        ArticleDetailDto article = articleApiService.getArticle(id);
        return R.ok().data(article);
    }

    @PostMapping
    public R createArticle(@RequestBody ArticleCreationDto articleCreationDto) {
        articleApiService.createArticle(articleCreationDto);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteArticle(@PathVariable("id") Long id) {
        articleApiService.deleteArticle(id);
        return R.ok();
    }

}
