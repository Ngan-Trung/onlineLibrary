package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.DTO.*;
import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User管理", description = "管理user的API")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/seeAll")
    @Operation(summary = "查看user，只有管理員能用", description = "")
    @ApiResponse(responseCode = "200", description = "成功查詢")
    public ResponseEntity<List<User>> seeAll() {
        return ResponseEntity.ok(userService.seeAllUser());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "用id刪除user，只有管理員能用", description = "")
    public ResponseEntity<User> deleteById(@Positive @Parameter(description = "user的id") @PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "刪除全部user，只有管理員能用", description = "")
    public ResponseEntity<User> deleteAll() {
        userService.deleteAllUser();
        return ResponseEntity.noContent().build();
    }
}

