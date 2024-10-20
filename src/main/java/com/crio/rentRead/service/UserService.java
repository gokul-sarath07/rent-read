package com.crio.rentRead.service;

import com.crio.rentRead.dto.CreateUser;
import com.crio.rentRead.dto.LoginUser;
import com.crio.rentRead.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(CreateUser user);
    void loginUser(LoginUser user);
    List<User> getAllUser();
    User getUser(Long userId);
}
