package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.DTO.*;
import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User管理", description = "管理user的API，包含註冊、登入等")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "註冊user")
    @ApiResponse(responseCode = "200", description = "成功註冊")
    @ApiResponse(responseCode = "400", description = "手機號碼被註冊過或資料不完整")
    public ResponseEntity<User> register(@Parameter(description = "暱稱、手機和密碼")@RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO.getPhoneNumber(), userDTO.getPassword(), userDTO.getUserName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "登入功能", description = "會回傳一個Session請記住")
    @ApiResponse(responseCode = "200", description = "成功登入")
    @ApiResponse(responseCode = "400", description = "帳號密碼出錯")
    public ResponseEntity<User> login(@Parameter(description = "手機和密碼") @RequestBody LoginDTO loginDTO) {
        User user = userService.login(loginDTO.getPhoneNumber(), loginDTO.getPassword());
        return ResponseEntity.ok(user);
    }
}

