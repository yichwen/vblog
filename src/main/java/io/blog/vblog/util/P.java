package io.blog.vblog.util;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class P<T> {

    private int page;
    private int size;
    private int totalPages;
    private long totalElements;
    private List<T> data = new ArrayList<>();

    private P(List<T> data) {
        this.data = data;
    }

    private P() {
    }

    public static <T> P<T> getInstance() {
        return new P<>();
    }

    public static <T> P<T> getInstance(T[] data) {
        return new P<>(Arrays.asList(data));
    }

    public static <T> P<T> getInstance(List<T> data) {
        return new P<>(data);
    }

    public static <T> P<T> getInstance(Page<T> page) {
        P<T> p = new P<>();
        p.size(page.getSize()).page(page.getNumber())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages());
        if (page.hasContent()) {
            p.data(page.getContent());
        }
        return p;
    }

    public static <T, U> P<U> getInstance(Page<T> page, Function<T, U> converter) {
        P<U> p = new P<>();
        p.size(page.getSize()).page(page.getNumber())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages());
        if (page.hasContent()) {
            p.data(page.getContent().stream().map(converter).collect(Collectors.toList()));
        }
        return p;
    }

    public P page(int page) {
        this.page = page;
        return this;
    }

    public P size(int size) {
        this.size = size;
        return this;
    }

    public P totalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public P totalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public P data(List<T> data) {
        this.data = data;
        return this;
    }

    public P data(T[] data) {
        this.data = Arrays.asList(data);
        return this;
    }

}
