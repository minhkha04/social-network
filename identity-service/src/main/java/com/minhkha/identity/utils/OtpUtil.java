package com.minhkha.identity.utils;

import java.security.SecureRandom;

public class OtpUtil {

    private OtpUtil() {
    }

    private static final SecureRandom random = new SecureRandom();

    public static String generateOtp() {
        int number = random.nextInt(1_000_000); // 0 -> 999999
        return String.format("%06d", number);   // luôn đủ 6 ký tự, thêm 0 phía trước
    }
}
