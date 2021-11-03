package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.User;

public class OTPDto {
    private Integer otpNumber;
    private String password;

    public OTPDto(Integer otpNumber, String password) {
        this.otpNumber = otpNumber;
        this.password = password;
    }

    public OTPDto() {
    }

    public Integer getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(Integer otpNumber) {
        this.otpNumber = otpNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
