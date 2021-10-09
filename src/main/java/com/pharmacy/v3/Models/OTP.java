package com.pharmacy.v3.Models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private Integer otpNumber;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "userId")
    private User user;
    private Date expiryDate;

    public OTP() {
    }

    public OTP(Integer otpNumber, User user, Date expiryDate) {
        this.otpNumber = otpNumber;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(Integer otpNumber) {
        this.otpNumber = otpNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public Date setExpiryDate(int mins){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, mins);
        this.expiryDate = now.getTime();
        return expiryDate;
    }
}
