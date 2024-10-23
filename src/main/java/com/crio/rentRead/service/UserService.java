package com.crio.rentRead.service;

import com.crio.rentRead.constants.Role;
import com.crio.rentRead.dto.CreateUser;
import com.crio.rentRead.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(CreateUser user);
    User updateRole(Long userId, String role);
    List<User> getAllUser();
    User getUser(Long userId);
    User getUserByEmail(String email);
}
