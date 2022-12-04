package io.blog.vblog.util;

import lombok.Data;

@Data
public class R<T> {

    private int code;
    private String message;
    private T data;

    public static <T> R<T> ok(int code) {
        return new R<>().code(code);
    }

    public static <T> R<T> ok() {
        return new R<>().code(200);
    }

    public static <T> R<T> error() { return new R<>().code(500).message("error"); }

    public static <T> R<T> error(String message) { return new R<>().code(500).message(message); }

    public static <T> R<T> error(int code, String message) { return new R<>().code(code).message(message); }

    public R code(int code) {
        this.code = code;
        return this;
    }

    public R data(T data) {
        this.data = data;
        return this;
    }

    public R message(String message) {
        this.message = message;
        return this;
    }

}
