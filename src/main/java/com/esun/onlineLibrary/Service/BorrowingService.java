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
public class BorrowingService {
    @Autowired
    private final BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private final InventoryRepository inventoryRepository;
    @Autowired
    private final UserRepository userRepository;

    public BorrowingService(BorrowingRecordRepository borrowingRecordRepository,
                            InventoryRepository inventoryRepository,
                            UserRepository userRepository) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public BorrowingRecord borrowBook(Long userId, String isbn) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new IllegalArgumentException("你是誰???");
        }
        Optional<Inventory> availableBooks = inventoryRepository.findByBookIsbn(isbn);
        if (!availableBooks.isPresent()) {
            throw new IllegalStateException("沒有這本書!!！");
        }
        Inventory bookToBorrow = availableBooks.get();
        if (bookToBorrow.getStatus().equals("borrowed")) {
            throw new IllegalStateException("書被別人拿走了!!！");
        }
        bookToBorrow.setStatus("borrowed");
        inventoryRepository.save(bookToBorrow);

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(optionalUser.get());
        record.setInventory(bookToBorrow);
        record.setBorrowingTime(LocalDateTime.now());
        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public BorrowingRecord returnBook(Long inventoryId) {
        Optional<BorrowingRecord> optionalRecord = borrowingRecordRepository
                .findByInventoryIdAndReturnTimeIsNull(inventoryId);

        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("你是不是打錯ID？");
        }
        BorrowingRecord record = optionalRecord.get();
        if(record.getInventory().getStatus().equals("available")){
            throw new IllegalArgumentException("你是不是忘了你已經還書了？");
        }

        record.setReturnTime(LocalDateTime.now());
        borrowingRecordRepository.save(record);

        Inventory inventory = record.getInventory();
        inventory.setStatus("available");
        inventoryRepository.save(inventory);
        return record;
    }
}

