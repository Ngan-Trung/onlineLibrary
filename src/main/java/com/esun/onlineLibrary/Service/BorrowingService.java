package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
import com.esun.onlineLibrary.Repository.DAO.BorrowingRecordJDBC;
import com.esun.onlineLibrary.Repository.DAO.InventoryJDBC;
import com.esun.onlineLibrary.Repository.DAO.UserJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long userId, String isbn) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            throw new IllegalArgumentException("你是誰???");
        }
        Optional<Inventory> book = inventoryRepository.findByBookIsbn(isbn);
        if (!book.isPresent()) {
            throw new IllegalArgumentException("沒有這本書!!！");
        }

        Inventory bookToBorrow = book.get();
        if (bookToBorrow.getStatus() != Status.AVAILABLE) {
            throw new IllegalArgumentException("這本書不能借!!！");
        }        
        bookToBorrow.setStatus(Status.BORROWED);
        inventoryRepository.save(bookToBorrow);

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(optionalUser.get());
        record.setInventory(bookToBorrow);
        record.setBorrowingTime(LocalDateTime.now());
        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public BorrowingRecord returnBook(String isbn) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByBookIsbn(isbn);
        if(!optionalInventory.isPresent()){
            throw new IllegalArgumentException("你是不是打錯ID？");
        }
        if(optionalInventory.get().getStatus() == Status.AVAILABLE){
            throw new IllegalArgumentException("你是不是忘了你已經還書了？");
        }

        Inventory inventory = optionalInventory.get();
        Optional<BorrowingRecord> optionalRecord = borrowingRecordRepository
                .findByInventoryIdAndReturnTimeIsNull(inventory.getId());
        if(!optionalRecord.isPresent()){
            throw new IllegalArgumentException("沒有相關出借紀錄？");
        }
        BorrowingRecord record = optionalRecord.get();
        record.setReturnTime(LocalDateTime.now());
        borrowingRecordRepository.save(record);

        inventory.setStatus(Status.AVAILABLE);
        inventoryRepository.save(inventory);
        return record;
    }

    public List<BorrowingRecord> findRecordByUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new IllegalArgumentException("沒有這個人");
        }
        return borrowingRecordRepository.findByUserId(userId);
    }

    public List<BorrowingRecord> findRecordByPhone(String phone) {
        Optional<User> user = userRepository.findByPhoneNumber(phone);
        if(!user.isPresent()){
            throw new IllegalArgumentException("沒有這個人");
        }
        return borrowingRecordRepository.findByUserId(user.get().getId());
    }
}

