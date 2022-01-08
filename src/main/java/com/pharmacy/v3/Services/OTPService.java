package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OTP;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.OTPRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Models.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class OTPService {
    private OTPRepository otpRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ResetPassword resetPassword;

    @Autowired
    public OTPService(OTPRepository otpRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ResetPassword resetPassword) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.resetPassword = resetPassword;
    }

    public int generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return otp;
    }

    public ResponseEntity<?> generateOPTSendEmail(Integer userId, String email) {
        try {
            if (otpRepository.existsByUserUserId(userId)) {
                OTP otp = otpRepository.findByUserUserId(userId);
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(otp.getUser());
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
                return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
            } else {
                User user = userRepository.findById(userId).get();
                OTP otp = new OTP();
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(user);
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
                return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }


    public ResponseEntity<?> checkOTPAvailable(Integer otpNumber) {
        try {
            if (otpRepository.existsByOtpNumber(otpNumber)) {
                OTP otp = otpRepository.findByOtpNumber(otpNumber);
                Date today = Calendar.getInstance().getTime();
                System.out.println(today);
                if ((otp.getExpiryDate()).compareTo(today) > 0) {
                    return ResponseEntity.ok().body(new MessageResponse("Success: Valid OTP"));
                } else {
                    return ResponseEntity.badRequest().body(new MessageResponse(("Error: OTP is expired. Please generate a new one!")));
                }
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Please generate a new one"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    public ResponseEntity<?> resetPassword(String password, Integer userId) {
        try {
            User user = userRepository.findById(userId).get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("Success: Password successfully updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
///////////////////////
    //// WEB
    ///////////////////////
    public ResponseEntity<?> sendOTPEmail(String email) {

        try {
            User u = userRepository.findByEmail(email);
            Integer userId=u.getUserId();
            if (otpRepository.existsByUserUserId(userId)) {
                OTP otp = otpRepository.findByUserUserId(userId);
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(otp.getUser());
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
            } else {
                User user = userRepository.findById(userId).get();
                OTP otp = new OTP();
                int otpNum = generateOTP();
                otp.setOtpNumber(otpNum);
                otp.setUser(user);
                otp.setExpiryDate(10);
                otpRepository.save(otp);
                resetPassword.sendEmail(otp, email);
            }
            return ResponseEntity.ok().body(new MessageResponse("Success: Email has been sent to you!"));


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
    public ResponseEntity<?> resetPasswordWithOTP(String password, Integer otp) {
        try {
            OTP u=otpRepository.findByOtpNumber(otp);
            Integer un=u.getUser().getUserId();
            User user = userRepository.findById(un).get();
            System.out.println(u);

            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok().body(new MessageResponse("Success: Password successfully updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
}
