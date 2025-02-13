package com.esun.onlineLibrary.Controller;

import com.esun.onlineLibrary.DTO.LoginDTO;
import com.esun.onlineLibrary.DTO.UserDTO;
import com.esun.onlineLibrary.Model.User;
import com.esun.onlineLibrary.Security.JwtUtil;
import com.esun.onlineLibrary.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User登入註冊", description = "User註冊、登入的API")
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "user登入")
    public ResponseEntity<String> login(@Valid @Parameter(description = "暱稱、手機和密碼") @RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getPhoneNumber(), loginDTO.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getPhoneNumber());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @Operation(summary = "註冊user")
    @ApiResponse(responseCode = "200", description = "成功註冊")
    public ResponseEntity<User> register(@Parameter(description = "暱稱、手機和密碼")@Valid @RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO.getPhoneNumber(), userDTO.getPassword(), userDTO.getUserName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register/admin")
    @Operation(summary = "註冊admin")
    @ApiResponse(responseCode = "200", description = "成功註冊")
    public ResponseEntity<User> registerAdmin(@Parameter(description = "暱稱、手機和密碼")@Valid @RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO.getPhoneNumber(), userDTO.getPassword(), userDTO.getUserName(), "ADMIN");
        return ResponseEntity.ok(user);
    }
}

