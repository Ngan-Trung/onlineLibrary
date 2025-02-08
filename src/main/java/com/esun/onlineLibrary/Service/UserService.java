package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String phoneNumber, String password, String userName) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("你早就註冊了!!!");
        }
        User user = new User();
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setUserName(userName);
        user.setRegistrationTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User login(String phoneNumber, String password) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("你帳號密碼打錯啦!!!");
        }
        User user = optionalUser.get();
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }
}

