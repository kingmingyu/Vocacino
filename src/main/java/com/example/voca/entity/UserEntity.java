package com.example.voca.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email; //모든 계정의 고유 식별자로 사용(소셜 로그인 포함)
    @Column(nullable = true)
    private String password; // 소셜 로그인 시 null

    private String username; // 사용자에게 보여지는 이름
    private String role;

    @Column(nullable = true)
    private String providerId; //일반 로그인 시 null

    private int learningData = 0; //유저 학습 날짜 (기본 0일차)
}