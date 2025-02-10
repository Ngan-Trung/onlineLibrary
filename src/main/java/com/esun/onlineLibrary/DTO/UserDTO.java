package com.esun.onlineLibrary.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message = "手機不能不填")
    private String phoneNumber;

    @NotBlank(message = "密碼不能為空")
    @Size(min = 6, max = 20, message = "密碼必須是6~20個字元")
    private String password;

    @NotBlank(message = "名稱不能沒寫")
    @Size(max = 50, message = "名稱不能超過50個字")
    private String userName;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

