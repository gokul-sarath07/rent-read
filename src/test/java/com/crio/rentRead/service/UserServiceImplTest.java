package com.crio.rentRead.service;

import com.crio.rentRead.constants.Role;
import com.crio.rentRead.dto.CreateUser;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private CreateUser createUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setEmail("testuser@gmail.com");
        testUser.setPassword("encodedPassword");
        testUser.setRole(Role.USER);

        createUser = new CreateUser();
        createUser.setFirstName("John");
        createUser.setLastName("Doe");
        createUser.setEmail("testuser@gmail.com");
        createUser.setPassword("password");
    }

    @Test
    void testRegisterUser_Success() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User registeredUser = userService.registerUser(createUser);

        assertNotNull(registeredUser);
        assertEquals("testuser@gmail.com", registeredUser.getEmail());
        assertEquals("encodedPassword", registeredUser.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateRole_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = userService.updateRole(1L, "ADMIN");

        assertNotNull(updatedUser);
        assertEquals(Role.ADMIN, updatedUser.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }
}