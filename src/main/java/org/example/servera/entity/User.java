package org.example.servera.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 45)
    private String username;

    @Column(name = "password", length = 300)
    private String password;

    @Column(name = "email", length = 45)
    private String email;

}