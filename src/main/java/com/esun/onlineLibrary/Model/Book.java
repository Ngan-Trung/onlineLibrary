package com.esun.onlineLibrary.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(length = 20)
    private String isbn;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
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

