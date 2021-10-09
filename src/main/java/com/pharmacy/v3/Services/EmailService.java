package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OTP;
import org.springframework.stereotype.Repository;

public interface EmailService {
    void sendEmail(OTP otp, String email);
}
