package com.minhkha.identity.service;

import com.minhkha.identity.entity.MailOtp;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.repository.MailOtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MailOtpService {

    MailOtpRepository mailOtpRepository;
    PasswordEncoder passwordEncoder;

    public void create(String email, String otp, LocalDateTime expiredTime) {
        mailOtpRepository.deleteById(email);

        MailOtp mailOtp = MailOtp.builder()
                .email(email)
                .otp(passwordEncoder.encode(otp))
                .expiredTime(expiredTime)
                .build();

        mailOtpRepository.save(mailOtp);
    }

    public void verify(String email, String otp) {
        MailOtp mailOtp = mailOtpRepository.findById(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        if (!passwordEncoder.matches(otp, mailOtp.getOtp())) {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        if (mailOtp.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        mailOtpRepository.deleteById(email);
    }
}
