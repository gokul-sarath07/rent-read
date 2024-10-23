package com.crio.rentRead.service;

import com.crio.rentRead.constants.Role;
import com.crio.rentRead.dto.CreateUser;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.exception.InvalidRoleException;
import com.crio.rentRead.exception.UserEmailAlreadyExistsException;
import com.crio.rentRead.exception.UserNotFoundException;
import com.crio.rentRead.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(CreateUser user) {
        log.info("Entered registerUser() method - CreateUser {}", user);
        try {
            User newUser = createUser(user);

            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            log.error("User with email: {} already exists", user.getEmail());
            throw new UserEmailAlreadyExistsException("Email already in use by another user. Please try with a different email");
        }
    }

    @Override
    public User updateRole(Long userId, String role) {
        log.info("Entered updateRole() method - userId: {}, role: {}", userId, role);
        Role newRole = getRole(role);
        User user = this.getUser(userId);

        user.setRole(newRole);

        return userRepository.save(user);
    }

    private Role getRole(String role) {
        log.info("Entered getRole() method - role: {}", role);
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException ex) {
            log.error("Invalid role: {} does not exist", role);
            throw new InvalidRoleException("Invalid Role: " + role);
        }
    }


    private User createUser(CreateUser user) {
        log.info("Entered createdUser() method - CreateUser: {}", user);
        User newUser = new User();

        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encodePassword(user.getPassword()));

        return newUser;
    }

    private String encodePassword(String password) {
        log.info("Entered encodePassword() method");
        return passwordEncoder.encode(password);
    }

    @Override
    public List<User> getAllUser() {
        log.info("Entered getAllUser() method");
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
        log.info("Entered getUser() method - userId: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id "+ userId + " not found"));
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Entered getUserByEmail() method - email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User with email "+ email + " not found"));
    }
}
