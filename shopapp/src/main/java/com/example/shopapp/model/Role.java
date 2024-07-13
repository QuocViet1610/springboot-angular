package com.example.shopapp.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;


    public static String ADMIN = "ADMIN";
    public static String USER = "USER";

//    @OneToMany(mappedBy = "roleId")
//    private Set<User> users;
}
