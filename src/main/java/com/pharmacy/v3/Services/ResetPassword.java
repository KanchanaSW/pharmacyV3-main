package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

@Service
@EnableAsync(proxyTargetClass = true)
public class ResetPassword implements EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public ResetPassword(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async("threadPoolTaskExecutor")
    public void sendEmail(OTP otp, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Subject: Password Reset Request");
        simpleMailMessage.setText("To reset your password, type the given OTP = " + otp.getOtpNumber());
        javaMailSender.send(simpleMailMessage);
    }
}
