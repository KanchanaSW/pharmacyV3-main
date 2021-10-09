package com.pharmacy.v3.Repositories;

import com.pharmacy.v3.Models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPRepository extends JpaRepository<OTP,Integer> {
    OTP findByOtpNumber(Integer otpNumber);

    OTP findByUserUserId(Integer userId);

    boolean existsByOtpNumber(Integer otpNumber);

    boolean existsByUserUserId(Integer userId);
}
