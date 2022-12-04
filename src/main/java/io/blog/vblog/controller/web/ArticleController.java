package io.blog.vblog.controller.web;

import io.blog.vblog.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/api/v1/articles")
public class ArticleController {

    @GetMapping
    public R getArticles() {
        return R.ok();
    }

    @GetMapping("/{id}")
    public R getArticle(@PathVariable("id") Long id) {
        return R.ok();
    }

}
