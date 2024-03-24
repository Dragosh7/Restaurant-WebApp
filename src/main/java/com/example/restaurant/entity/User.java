package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(unique = true)
    private String username;
    @NonNull
    private String password;
    private String role;
    private boolean active = false;
    @NaturalId(mutable = true)
    private String emailAddress;
}
