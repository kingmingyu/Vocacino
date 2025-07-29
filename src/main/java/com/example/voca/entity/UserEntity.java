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

    private String username;
    private String email;
    private String role;
}

/*
@Entity
@Table(name = "users") // 테이블명은 일반적으로 복수형을 사용합니다.
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 모든 계정의 고유 식별자로 활용 (소셜 로그인 포함)

    private String password; // 일반 회원가입용. 소셜 로그인 시에는 null.

    @Column(nullable = false)
    private String username; // 사용자에게 보여지는 이름

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 사용자 권한 (예: USER, ADMIN)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType provider; // 가입/로그인 방식: LOCAL, GOOGLE, KAKAO, NAVER 등

    private String providerId; // 소셜 로그인 제공자별 고유 ID. 일반 회원가입 시에는 null.

    private String profileImageUrl; // 소셜 로그인 프로필 이미지 URL (선택적)

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    private boolean emailVerified = false; // 이메일 인증 여부

    // 리프레시 토큰은 DB에 저장하지 않고 Redis 등 별도 스토리지에 저장하는 것이 일반적이나,
    // 필요하다면 여기에 해싱하여 저장하는 것도 가능
    // private String refreshToken;

    // 생성자, Getter, Setter, toString 등
    // ...

    // ProviderType Enum 예시
    public enum ProviderType {
        LOCAL, GO_OGLE, KAKAO, NAVER // Google, Kakao, Naver 등의 실제 값으로 변경하세요.
    }

    // Role Enum 예시 (현재와 동일)
    public enum Role {
        USER, ADMIN
    }
}
 */