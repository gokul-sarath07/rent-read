package com.crio.rentRead.controller;

import com.crio.rentRead.dto.CreateUser;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.crio.rentRead.config.PathConstants.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping(REGISTER_USER)
    public ResponseEntity<User> registerUser(@Valid @RequestBody CreateUser createUser) {
        User user = userService.registerUser(createUser);

        return ResponseEntity.ok(user);
    }

    @GetMapping(GET_ALL_USER)
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUser();

        return ResponseEntity.ok(users);
    }

    @GetMapping(GET_A_USER)
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        User user = userService.getUser(userId);

        return ResponseEntity.ok(user);
    }

    @PutMapping(UPDATE_USER_ROLE)
    public ResponseEntity<User> updateUserRole(@PathVariable("userId") Long userId, @RequestParam(required = true) String role) {
        User user = userService.updateRole(userId, role);

        return ResponseEntity.ok(user);
    }
}
