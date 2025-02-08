package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
import com.esun.onlineLibrary.Repository.DAO.InventoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private BookRepository bookRepository;

    public Inventory findByIsbn(String isbn) {
        Optional<Inventory> inventory = inventoryRepository.findByBookIsbn(isbn);
        if(inventory.isPresent()){
            return inventory.get();
        }
        throw new IllegalArgumentException("沒有這本書");
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    @Transactional
    public Inventory addInventory(Book book) {
        if(inventoryRepository.existsByBookIsbn(book.getIsbn())){
            throw new IllegalArgumentException("有這本書了");
        }
        bookRepository.save(book);
        Inventory inventory = new Inventory();
        inventory.setBook(book);
        inventory.setStoreTime(LocalDateTime.now());
        inventory.setStatus(Status.AVAILABLE);
        return inventoryRepository.save(inventory);
    }

    @Transactional
    public void deleteBook(String isbn) {
        Optional<Book> optionalBook = bookRepository.findById(isbn);
        Optional<Inventory> inventory = inventoryRepository.findByBookIsbn(isbn);

        if (!optionalBook.isPresent()) {
            throw new IllegalArgumentException("沒有這本書啦！");
        }

        if(inventory.get().getStatus() == Status.BORROWED){
            throw new IllegalArgumentException("書被借走了，無法刪除");
        }

        Book book = optionalBook.get();
        inventoryRepository.deleteByBookIsbn(isbn);
        bookRepository.delete(book);
    }
}

