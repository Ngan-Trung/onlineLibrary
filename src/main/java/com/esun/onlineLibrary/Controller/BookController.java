package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.*;
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

    @GetMapping("/{isbn}")
    @Operation(summary = "用isbn查書")
    @ApiResponse(responseCode = "200", description = "有查到書")
    @ApiResponse(responseCode = "400", description = "沒查到")
    public ResponseEntity<Book> getBookByIsbn(@Parameter(description = "書isbn") @PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }


    @GetMapping("/search/{name}")
    @Operation(summary = "用名字查書")
    @ApiResponse(responseCode = "200", description = "有查到書")
    @ApiResponse(responseCode = "400", description = "沒查到")
    public ResponseEntity<List<Book>> searchBooks(@Parameter(description = "書名") @PathVariable String name) {
        return ResponseEntity.ok(bookService.searchBooksByName(name));
    }

    @DeleteMapping("/{isbn}")
    @Operation(summary = "用isbn刪除書")
    @ApiResponse(responseCode = "200", description = "有刪到書")
    @ApiResponse(responseCode = "400", description = "沒有這本書或是被借走了")
    public ResponseEntity<String> deleteBook(@Parameter(description = "書isbn") @PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.ok("ISBN：" + isbn + " 已被刪除!!!");
    }
}

