package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.concurrent.Executor;

@Service
@EnableAsync(proxyTargetClass = true)
public class ResetPassword implements EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public ResetPassword(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async("threadPoolTaskExecutor")
    public void sendEmail(OTP otp, String email) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception{
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage ,true);
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(email));
                helper.setSubject("Subject: Password Reset Request");

                String mailContent = "<p><b>Dear </b> User </p>";
                mailContent+="<p><b>Please use the verification code below on the PMS website</b></p>";
                mailContent+="<p><b>OTP code is : </b></p>";
                mailContent+="<p><b>&emsp;&emsp;&emsp;    "+otp.getOtpNumber()+"</b></p>";
                mailContent+="<p><b>If you didn't request this, you can ignore this email </b></p>";

                helper.setText(mailContent,true);
            }
        };
        javaMailSender.send(preparator);
    }

    @Async("threadPoolTaskExecutor")
    public void contactAdmin(String email,String message) throws MessagingException {
        String adminMail="skw22456@gmail.com";
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception{
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage ,true);
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(adminMail));
                helper.setSubject("Subject: Contact Admin");
                helper.setFrom(new InternetAddress(email));

                String mailContent = "<p><b>Dear </b> Admin </p>";
                mailContent+="<p><b>My email address is :&emsp;  "+email+"</b></p>";
                mailContent+="<p><b>"+message+"    </b></p>";
                //mailContent+="<p><b>  </b></p>";

                helper.setText(mailContent,true);
                mimeMessage.setSentDate(new Date());
            }
        };
        javaMailSender.send(preparator);
    }
}
