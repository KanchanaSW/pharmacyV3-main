package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Services.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/otp")
@RestController
public class OTPController {
    private OTPService otpService;

    @Autowired
    public OTPController(OTPService otpService) {
        this.otpService = otpService;
    }

    /*@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/request-password-change/{userId}")
    public ResponseEntity<?> generateOPTSendEmail(@PathVariable Integer userId, @RequestParam String email) {
        return otpService.generateOPTSendEmail(userId, email);
    }
*/

    //step 1
    @PostMapping(value = "/send-otp-change")
    public ResponseEntity<?> sendOTPEmail(@RequestParam String email) {
        System.out.println("***************"+email);
        return otpService.sendOTPEmail(email);

    }

    //step 2
    @GetMapping(value = "/valid-check")
    public ResponseEntity<?> checkOTPAvailable(@RequestParam Integer otpNumber) {
        return otpService.checkOTPAvailable(otpNumber);
    }

    //step 3
    @PostMapping(value = "/reset")
    public ResponseEntity<?> resetPassword(@RequestParam Integer otpNumber, @RequestParam String password) {
        return otpService.resetPasswordWithOTP(password, otpNumber);
        //return otpService.resetPassword(newPassword, userId);
    }



}
