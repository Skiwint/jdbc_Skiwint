package com.example.cage_api.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "serName", nullable = false)
    private String serName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Column(name = "status", nullable = false)
    private String status;

}
