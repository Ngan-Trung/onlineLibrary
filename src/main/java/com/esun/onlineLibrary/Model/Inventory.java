package com.esun.onlineLibrary.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;


@Entity
@Table(name = "inventory")
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "isbn", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Book book;

    @Column(name = "store_time", nullable = false)
    private LocalDateTime storeTime;

    @Column(nullable = false, length = 20)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(LocalDateTime storeTime) {
        this.storeTime = storeTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

