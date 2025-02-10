package com.esun.onlineLibrary.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BorrowDTO {
    @NotNull(message = "使用者ID不能不寫")
    private Long userId;
    @NotBlank(message = "有ISBN才能找書")
    private String isbn;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
