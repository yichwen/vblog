package io.blog.vblog.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ArticleStatus {

    DRAFTED,
    PUBLISHED,
    DELETED;

    @JsonValue
    public int getValue() {
        return ordinal();
    }

    public static ArticleStatus from(String status) {
        try {
            int ordinal = Integer.parseInt(status);
            for (ArticleStatus value : values()) {
                if (value.ordinal() == ordinal) {
                    return value;
                }
            }
            return null;
        } catch (NumberFormatException e1) {
            try {
                return valueOf(status);
            } catch (IllegalArgumentException | NullPointerException e2) {
                return null;
            }
        }
    }

}
