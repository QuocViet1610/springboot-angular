package com.example.shopapp.model;


import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, length = 225)
    private String token;

    @Column(name = "token_type", nullable = false, length = 50)
    private String tokenType;

    @Column(name = "revoked")
    private boolean revoked;

    @Column(name = "exprired")
    private boolean exprired;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User userId;

}
