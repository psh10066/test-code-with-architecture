package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.common.service.port.UuidHolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Clock;

@Getter
@Builder
@AllArgsConstructor
public class User {
    private final Long id;
    private final String email;
    private final String nickname;
    private final String address;
    private final String certificationCode;
    private final UserStatus status;
    private final Long lastLoginAt;

    public static User from(UserCreate userCreate, UuidHolder uuidHolder) {
        return User.builder()
            .email(userCreate.getEmail())
            .nickname(userCreate.getNickname())
            .address(userCreate.getAddress())
            .certificationCode(uuidHolder.random())
            .status(UserStatus.PENDING)
            .build();
    }

    public User update(UserUpdate userUpdate) {
        return User.builder()
            .id(id)
            .email(email)
            .nickname(userUpdate.getNickname())
            .address(userUpdate.getAddress())
            .certificationCode(certificationCode)
            .status(status)
            .lastLoginAt(lastLoginAt)
            .build();
    }

    public User login(ClockHolder clockHolder) {
        return User.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .address(address)
            .status(status)
            .certificationCode(certificationCode)
            .lastLoginAt(clockHolder.millis())
            .build();
    }

    public User certificate(String certificationCode) {
        if (!this.certificationCode.equals(certificationCode)) {
            throw new CertificationCodeNotMatchedException();
        }
        return User.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .address(address)
            .status(UserStatus.ACTIVE)
            .certificationCode(certificationCode)
            .lastLoginAt(Clock.systemUTC().millis())
            .build();
    }
}
