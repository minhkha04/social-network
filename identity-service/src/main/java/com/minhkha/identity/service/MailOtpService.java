package com.minhkha.identity.service;

import com.minhkha.identity.entity.MailOtp;
import com.minhkha.identity.expection.AppException;
import com.minhkha.identity.expection.ErrorCode;
import com.minhkha.identity.repository.MailOtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MailOtpService {

    MailOtpRepository mailOtpRepository;

    public void create(String email, String otp) {
        mailOtpRepository.deleteById(email);

        MailOtp mailOtp = MailOtp.builder()
                .email(email)
                .otp(otp)
                .expiredTime(LocalDateTime.now().plusMinutes(5)).build();

        mailOtpRepository.save(mailOtp);
    }

    public void verify(String email, String otp) {
        MailOtp mailOtp = mailOtpRepository.findById(email)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));
        if (!mailOtp.getOtp().equals(otp)) {
            throw new AppException(ErrorCode.OTP_INVALID);
        }
        if (mailOtp.getExpiredTime().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }
        mailOtpRepository.deleteById(email);
    }
}
