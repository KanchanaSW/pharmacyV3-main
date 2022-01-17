package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OTP;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;

@EnableAsync(proxyTargetClass = true)
public interface EmailService {
    void sendEmail(OTP otp, String email) throws MessagingException;
    void contactAdmin(String email,String message) throws MessagingException;
}
