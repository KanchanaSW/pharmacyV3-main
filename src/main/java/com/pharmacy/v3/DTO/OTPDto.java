package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.User;

public class OTPDto {
    private Integer otpNumber;
    private String password;
    private String password2;

    public OTPDto(Integer otpNumber, String password) {
        this.otpNumber = otpNumber;
        this.password = password;
    }

    public OTPDto() {
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
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
