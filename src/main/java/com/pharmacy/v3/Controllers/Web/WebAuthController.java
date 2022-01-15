package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.OTPDto;
import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.OTP;
import com.pharmacy.v3.Models.Role;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.OTPRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Services.AuthService;
import com.pharmacy.v3.Services.OTPService;
import com.pharmacy.v3.Services.RoleService;
import com.pharmacy.v3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WebAuthController {

    @Autowired
    public UserService userService;
    @Autowired
    public AuthService authService;
    @Autowired
    public OTPService otpService;
    @Autowired
    public UserRepository userRepository;


    @GetMapping("/Home")
    public ModelAndView getLogin(String error){
        ModelAndView modelAndView=new ModelAndView();
        if (error != null){
            modelAndView.addObject("error","bad");
        }
        return modelAndView;
    }

    @GetMapping("/UserHome")
    public String getUserHome(){return "UserHome";}

    @GetMapping("/UserHomePending")
    public String getUserHomePending(){return "AccountPending";}

    @GetMapping("/AdminHome")
    public String getAdminHome(){return "AdminHome";} //Admin page

    @GetMapping("/error")
    public String getError(Model model){
        model.addAttribute("error","Crediential error!");
        return "/error";
    }

    @GetMapping("/SuccessLogin")
    public String successLogin(Authentication authentication){
        User userType = userService.directUserType(authentication.getName());
        System.out.println("//////"+userType.getStatus());
        if(userType.getRole().getRoleId().equals(1)){
            return "redirect:/AdminHome";
        }else if(userType.getRole().getRoleId().equals(2) && userType.getStatus().equals("verified")){
            return "redirect:/UserHome";
        }else if (userType.getRole().getRoleId().equals(2) && userType.getStatus().equals("pending") || userType.getStatus().equals("blacklisted")){
            return "redirect:/UserHomePending";
        }else {
            return "/Home";
        }
    }

    @GetMapping("/RegisterPage")
    public String registerPage(Model model){
        model.addAttribute("Register", new UserDTO());
        //Binding the form fields of JSP to Object
        return "/Register";
    }

    @PostMapping("/Register")
    public String registerUser(@ModelAttribute("Registers") UserDTO userDTO,Model model){
        try{
            String roleName="ROLE_USER";
            ResponseEntity<?> user = authService.registerUserService(userDTO,roleName);
            //Takes in the bound data from the JSP
            //System.out.println("/////////////////"+user.toString());
            if(user.getStatusCode()== HttpStatus.BAD_REQUEST){
                model.addAttribute("uError","Username already taken!");
                //return vals
                model.addAttribute("uName",userDTO.getUsername());
                model.addAttribute("rEmail",userDTO.getEmail());
                model.addAttribute("rPhone",userDTO.getPhone());
                //Binding error message
            }else if (user.getStatusCode()==HttpStatus.NOT_ACCEPTABLE){
                model.addAttribute("eError","Email already taken!");
                //return vals
                model.addAttribute("uName",userDTO.getUsername());
                model.addAttribute("rEmail",userDTO.getEmail());
                model.addAttribute("rPhone",userDTO.getPhone());
            }
            else{
                model.addAttribute("success","User Added Successfully");
                //Binding success message
            }
        }catch (Exception e){
            model.addAttribute("error","Failed To Add User");
            //Binding error message for exceptions
        }
        return "Register";
    }


    @GetMapping("/ForgotPasswordPage")
    public String forward2ForgotPassword(Model model){
        model.addAttribute("ForgotPassword",new OTP());
        return "/ForgotPassword";
    }

    @PostMapping("/SendOTPEmail")
    public String sendOTPEmail(@ModelAttribute("email") String email,Model model){
        ResponseEntity<?> sendEmail=otpService.sendOTPEmail(email);
        if (sendEmail.getStatusCodeValue()==200){
            model.addAttribute("msg","ok");
        }else{
            model.addAttribute("msg","fail");
        }

        //return "ForgotPasswordPage";
        return "ValidateOTP";
    }

    @PostMapping("/ValidateOTP")
    public String validateOTP(@ModelAttribute("otp") Integer otp,Model model){
        ResponseEntity<?> validCheck=otpService.checkOTPAvailable(otp);
        if (validCheck.getStatusCodeValue()==200){
            model.addAttribute("msg","ok");
            //add the otp to next page div
            model.addAttribute("otp",otp);
        }else{
            model.addAttribute("msg","fail");
        }

        return "ResetPassword";
    }

    @PostMapping("/ResetPassword")
    public String resetPassword(@ModelAttribute("details")OTPDto otpDto, Model model){
        String password=otpDto.getPassword();
        Integer otpNumber=otpDto.getOtpNumber();
        ResponseEntity<?> resetPass=otpService.resetPasswordWithOTP(password,otpNumber);
        if (resetPass.getStatusCodeValue()==200){
            model.addAttribute("error","passwordResetOk");
        }else{
            model.addAttribute("error","fail");
        }
        return "/Home";
    }
}
