package com.esun.onlineLibrary.Service;

import com.esun.onlineLibrary.Model.*;
import com.esun.onlineLibrary.Repository.*;
import com.esun.onlineLibrary.Repository.DAO.UserJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("沒有這個帳號!!!");
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("你帳號密碼打錯啦!!!");
        }
        user.setLastLoginTime(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    public List<User> seeAllUser() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }
}

