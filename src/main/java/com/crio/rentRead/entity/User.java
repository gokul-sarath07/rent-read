package com.crio.rentRead.entity;

import com.crio.rentRead.constants.Role;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(unique = true)
    private String email;

    private String password;

    public User() {}
}
