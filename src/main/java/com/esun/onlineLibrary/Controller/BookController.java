package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "書管理", description = "管理書的API，像是查詢")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/public/{isbn}")
    @Operation(summary = "用isbn查書")
    @ApiResponse(responseCode = "200", description = "有查到書")
    @ApiResponse(responseCode = "400", description = "沒查到")
    public ResponseEntity<Book> getBookByIsbn(@NotBlank @Parameter(description = "書isbn") @PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }


    @GetMapping("/public/search/{name}")
    @Operation(summary = "用名字查書")
    @ApiResponse(responseCode = "200", description = "有查到書")
    @ApiResponse(responseCode = "400", description = "沒查到")
    public ResponseEntity<List<Book>> searchBooks(@NotBlank @Parameter(description = "書名") @PathVariable String name) {
        return ResponseEntity.ok(bookService.searchBooksByName(name));
    }

    @GetMapping("/public/search/all")
    @Operation(summary = "查看全部書的庫存")
    public ResponseEntity<List<Book>> getAllBook() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping("/admin/update")
    @Operation(summary = "更新書資料")
    @ApiResponse(responseCode = "200", description = "更新成功")
    @ApiResponse(responseCode = "400", description = "沒這本書")
    public ResponseEntity<Book> updateBooks(@Valid @Parameter(description = "書的資料") @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateName(book));
    }
}

