package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    public Book findBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findById(isbn);
        if(book.isPresent()){
            return book.get();
        }
        throw new IllegalArgumentException("找不到書啦！!!");
    }

    public List<Book> searchBooksByName(String name) {
        return bookRepository.findByNameContaining(name);
    }

    @Transactional
    public void deleteBook(String isbn) {
        Optional<Book> optionalBook = bookRepository.findById(isbn);
        Optional<Inventory> inventory = inventoryRepository.findByBookIsbn(isbn);

        if (!optionalBook.isPresent()) {
            throw new IllegalArgumentException("沒有這本書啦！");
        }

        if(inventory.get().getStatus().equals("borrowed")){
            throw new IllegalArgumentException("書被借走了，無法刪除");
        }

        Book book = optionalBook.get();
        bookRepository.delete(book);
    }
}
