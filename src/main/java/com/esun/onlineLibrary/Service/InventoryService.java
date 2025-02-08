package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
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

    public Inventory findByIsbn(String isbn) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByBookIsbn(isbn);
        if(optionalInventory.isPresent()){
            return optionalInventory.get();
        }
        return null;
    }

    @Transactional
    public Inventory addInventory(Book book) {

        Optional<Inventory> optionalBook = inventoryRepository.findByBookIsbn(book.getIsbn());
        if(optionalBook.isPresent()){
            throw new IllegalArgumentException("有這本書了");
        }
        Inventory inventory = new Inventory();
        inventory.setBook(book);
        inventory.setStoreTime(LocalDateTime.now());
        inventory.setStatus(Status.AVAILABLE);
        return inventoryRepository.save(inventory);
    }
}

