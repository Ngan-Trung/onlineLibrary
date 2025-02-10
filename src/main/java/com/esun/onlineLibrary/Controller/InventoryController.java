package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.Model.Book;
import com.esun.onlineLibrary.Model.Inventory;
import com.esun.onlineLibrary.Service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Tag(name = "庫存管理", description = "對圖書館的庫存做管理")
@Validated
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/search/{isbn}")
    @Operation(summary = "用isbn查書的庫存")
    @ApiResponse(responseCode = "200", description = "有這本書")
    @ApiResponse(responseCode = "400", description = "沒有這本書")
    public ResponseEntity<Inventory> getInventoryByIsbn(@Parameter(description = "書的isbn") @PathVariable String isbn) {
        return ResponseEntity.ok(inventoryService.findByIsbn(isbn));
    }

    @GetMapping("/search/all")
    @Operation(summary = "查看全部書的庫存")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.findAll());
    }

    @PostMapping("/add")
    @Operation(summary = "新增庫藏書")
    @ApiResponse(responseCode = "200", description = "收藏成功")
    @ApiResponse(responseCode = "400", description = "已經收藏過了")
    public ResponseEntity<Inventory> addInventory(@Valid @Parameter(description = "書的完整資料") @RequestBody Book book) {
        return ResponseEntity.ok(inventoryService.addInventory(book));
    }

    @DeleteMapping("/{isbn}")
    @Operation(summary = "用isbn刪除書")
    @ApiResponse(responseCode = "200", description = "有刪到書")
    @ApiResponse(responseCode = "400", description = "沒有這本書或是被借走了")
    public ResponseEntity<String> deleteBook(@NotBlank @Parameter(description = "書isbn") @PathVariable String isbn) {
        inventoryService.deleteBook(isbn);
        return ResponseEntity.ok("ISBN：" + isbn + " 已被刪除!!!");
    }
}

