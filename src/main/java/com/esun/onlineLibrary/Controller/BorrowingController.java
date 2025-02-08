package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.DTO.*;
import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@Tag(name = "租借管理", description = "登記借書還書")
public class BorrowingController {
    @Autowired
    private BorrowingService borrowingService;

    @PostMapping("/borrow")
    @Operation(summary = "借書")
    @ApiResponse(responseCode = "200", description = "成功租借")
    @ApiResponse(responseCode = "400", description = "圖書館現在沒有這本書")
    public ResponseEntity<BorrowingRecord> borrowBook(@Parameter(description = "User資料和書的資料") @RequestBody BorrowDTO borrowDTO) {
        BorrowingRecord record = borrowingService.borrowBook(borrowDTO.getUserId(), borrowDTO.getIsbn());
        return ResponseEntity.ok(record);
    }

    @PostMapping("/return/{isbn}")
    @Operation(summary = "還書")
    @ApiResponse(responseCode = "200", description = "成功還書")
    @ApiResponse(responseCode = "400", description = "沒有這本書、或已經還了")
    public ResponseEntity<BorrowingRecord> returnBook(@Parameter(description = "書的isbn")@PathVariable String isbn) {
        BorrowingRecord record = borrowingService.returnBook(isbn);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "搜尋借書紀錄")
    @ApiResponse(responseCode = "200", description = "查詢成功")
    @ApiResponse(responseCode = "400", description = "沒有這個user")
    public ResponseEntity<List<BorrowingRecord>> findByUserId(@Parameter(description = "輸入UserID")@PathVariable Long id) {
        List<BorrowingRecord> records = borrowingService.findRecordByUser(id);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/findByPhone/{phone}")
    @Operation(summary = "搜尋借書紀錄")
    @ApiResponse(responseCode = "200", description = "查詢成功")
    @ApiResponse(responseCode = "400", description = "沒有這個user")
    public ResponseEntity<List<BorrowingRecord>> findByPhone(@Parameter(description = "輸入UserID")@PathVariable String phone) {
        List<BorrowingRecord> records = borrowingService.findRecordByPhone(phone);
        return ResponseEntity.ok(records);
    }
}

