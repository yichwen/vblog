package io.blog.vblog.service;

import io.blog.vblog.dto.CategoryDto;
import io.blog.vblog.dto.CategoryUpdateDto;
import io.blog.vblog.entity.Category;
import io.blog.vblog.repository.CategoryRepository;
import io.blog.vblog.util.P;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryApiService {

    @Autowired
    private CategoryRepository categoryRepository;

    private static Function<Category, CategoryDto> mapper = category -> {
        CategoryDto categoryDto = new CategoryDto();
        BeanUtils.copyProperties(category, categoryDto);
        return categoryDto;
    };

    public P<CategoryDto> getCategoriesPage(Pageable pageable) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageable);
        return P.getInstance(categoriesPage, mapper);
    }

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        return mapper.apply(getCategory(id));
    }

    public void createCategory(CategoryUpdateDto categoryUpdateDto) {
        boolean exists = categoryRepository.existsByName(categoryUpdateDto.getName());
        if (exists) {
            String message = String.format("category with name [%s] already existed", categoryUpdateDto.getName());
            throw new IllegalStateException(message);
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryUpdateDto, category);
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category category = getCategory(id);
        BeanUtils.copyProperties(categoryUpdateDto, category);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategory(id);
        if (category.getArticles().size() > 0) {
            String message = String.format("can't delete category with id [%d] contains articles", id);
            throw new IllegalStateException(message);
        }
        categoryRepository.delete(category);
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            String message = String.format("category with id [%d] is not found", id);
            throw new IllegalStateException(message);
        });
    }

}
