package com.esun.onlineLibrary.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "books")
@Validated
public class Book {

    @Id
    @Column(length = 20)
    @NotBlank
    @Size(min = 1, max = 20)
    private String isbn;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(min = 1, max = 50)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

