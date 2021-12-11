package com.eea.pms.Model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OTP {
    private long id;
    private Integer otpNumber;
    private Integer userId;
    private Date expiryDate;
    private String password;

    public OTP(Integer otpNumber, Integer userId, Date expiryDate) {
        this.otpNumber = otpNumber;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public OTP(Integer otpNumber) {
        this.otpNumber = otpNumber;
    }

    public OTP(Integer otpNumber, String password) {
        this.otpNumber = otpNumber;
        this.password = password;
    }
}
