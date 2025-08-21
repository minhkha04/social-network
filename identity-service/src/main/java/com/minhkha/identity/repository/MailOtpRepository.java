package com.minhkha.identity.repository;

import com.minhkha.identity.entity.MailOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailOtpRepository extends JpaRepository<MailOtp, String> {
}
