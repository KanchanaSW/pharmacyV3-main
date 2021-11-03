package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Services.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/otp")
@RestController
public class OTPController {
    private OTPService otpService;

    @Autowired
    public OTPController(OTPService otpService) {
        this.otpService = otpService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/request-password-change/{userId}")
    public ResponseEntity<?> generateOPTSendEmail(@PathVariable Integer userId, @RequestParam String email) {
        return otpService.generateOPTSendEmail(userId, email);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/valid-check")
    public ResponseEntity<?> checkOTPAvailable(@RequestParam Integer otpNumber) {
        return otpService.checkOTPAvailable(otpNumber);
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/reset/{userId}")
    public ResponseEntity<?> resetPassword(@PathVariable Integer userId, @RequestParam String newPassword) {
        return otpService.resetPassword(newPassword, userId);
    }


//working
    @PostMapping(value = "/send-otp-change")
    public ResponseEntity<?> sendOTPEmail(@RequestParam String email) {
        return otpService.sendOTPEmail(email);

    }
}
