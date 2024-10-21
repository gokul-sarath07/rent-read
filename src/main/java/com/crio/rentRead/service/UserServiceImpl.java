package com.crio.rentRead.service;

import com.crio.rentRead.dto.CreateUser;
import com.crio.rentRead.dto.LoginUser;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.exception.UserEmailAlreadyExistsException;
import com.crio.rentRead.exception.UserNotFoundException;
import com.crio.rentRead.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User registerUser(CreateUser user) {
        try {
            User newUser = createUser(user);

            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            throw new UserEmailAlreadyExistsException("Email already in use by another user. Please try with a different email");
        }
    }

    private User createUser(CreateUser user) {
        User newUser = new User();

        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(encodePassword(user.getPassword()));

        return newUser;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public void loginUser(LoginUser user) {
        // TODO
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User with id "+ userId + " not found"));
    }
}
