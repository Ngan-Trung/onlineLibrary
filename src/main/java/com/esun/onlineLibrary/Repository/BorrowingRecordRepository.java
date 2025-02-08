package com.esun.onlineLibrary.Repository;

import com.esun.onlineLibrary.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findByUserId(Long userId);

    Optional<BorrowingRecord> findByInventoryIdAndReturnTimeIsNull(Long inventoryId);
}

