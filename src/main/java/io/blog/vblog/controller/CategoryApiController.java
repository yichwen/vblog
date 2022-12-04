package io.blog.vblog.controller;

import io.blog.vblog.dto.CategoryDto;
import io.blog.vblog.dto.CategoryUpdateDto;
import io.blog.vblog.service.CategoryApiService;
import io.blog.vblog.util.P;
import io.blog.vblog.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryApiController {

    @Autowired
    private CategoryApiService categoryApiService;

    @GetMapping
    public R getCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size) {
        P<CategoryDto> categories = categoryApiService.getCategoriesPage(PageRequest.of(page, size));
        return R.ok().data(categories);
    }

    @GetMapping("/all")
    public R getCategories() {
        List<CategoryDto> categories = categoryApiService.getCategories();
        return R.ok().data(categories);
    }

    @GetMapping("{id}")
    public R getCategory(@PathVariable("id") Long id) {
        CategoryDto category = categoryApiService.getCategoryById(id);
        return R.ok().data(category);
    }

    @PostMapping
    public R createCategory(@RequestBody CategoryUpdateDto categoryUpdateDto) {
        categoryApiService.createCategory(categoryUpdateDto);
        return R.ok();
    }

    @PutMapping("{id}")
    public R updateCategory(@PathVariable("id") Long id, @RequestBody CategoryUpdateDto categoryUpdateDto) {
        categoryApiService.updateCategory(id, categoryUpdateDto);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteCategory(@PathVariable("id") Long id) {
        categoryApiService.deleteCategory(id);
        return R.ok();
    }

}
