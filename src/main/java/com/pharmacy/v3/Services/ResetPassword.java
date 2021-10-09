package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OTP;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ResetPassword implements EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public ResetPassword(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(OTP otp, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Subject: Password Reset Request");
        simpleMailMessage.setText("Body: To reset your password, type the given OTP =" + otp.getOtpNumber());
        javaMailSender.send(simpleMailMessage);
    }
}
