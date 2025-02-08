package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
import com.esun.onlineLibrary.Repository.DAO.BookJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> searchBooksByName(String name) {
        return bookRepository.findByNameContaining(name);
    }

    public Book updateName(Book book) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(book.getIsbn());
        if(!optionalBook.isPresent()){
            throw new IllegalArgumentException("沒有這本書");
        }
        Book updatedBook = optionalBook.get();
        updatedBook.setName(book.getName());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setIntroduction(book.getIntroduction());
        return bookRepository.save(updatedBook);
    }
}
