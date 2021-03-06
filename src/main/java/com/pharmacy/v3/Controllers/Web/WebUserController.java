package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.UserDTO;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebUserController {
    private UserService userService;

    @Autowired
    public WebUserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/ViewAllUsers")
    public String getAllUsers(Model model) {
        List<User> allUsers=userService.getAllUsers();
        model.addAttribute("users",allUsers);
        return "ViewAllUsers";
    }

    //redirecting to update user page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/UpdateAUser")
    public String UpdateUserPage(Authentication authentication, Model model){
        User userType = userService.directUserType(authentication.getName());
        User u=userService.findUser(userType.getUserId());

        model.addAttribute("UserDetails",u);
        model.addAttribute("UpdateUser", new UserDTO());
        return "UpdateUser";

    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/UpdateMyAcc")
    public String updateUser(@ModelAttribute("UpdateUser") UserDTO userdto, Model model) {
        try{
            userService.updateUser(userdto);
            model.addAttribute("success", "Successfully Updated The User");
        }catch(Exception e){
            model.addAttribute("error", "Failed To Update The User");
        }
        return "/UpdateUser";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/DeleteUser/{userId}")
    public String deleteUser(@PathVariable(name = "userId")Integer userId, Model model){
        try{
            userService.blacklistUser(userId);
            model.addAttribute("success","User Was Successfully Deleted");

        }catch(Exception e){
            model.addAttribute("error","Failed To Delete The User");

        }
        return "redirect:/ViewAllUsers";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/UpdateStatus/{userId}")
    public String updateUserStatus(@PathVariable(name = "userId")Integer userId, Model model){
        try{
            userService.updateStatus(userId);
            model.addAttribute("success","User Was Successfully updated");

        }catch(Exception e){
            model.addAttribute("error","Failed To update The User");

        }
        return "redirect:/ViewAllUsers";
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/Error404")
    public String error404(Model model){
        model.addAttribute("error","error");
        return "Error";
    }
/*
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/error")
    public String error(Model model){
        model.addAttribute("error","error error");
        return "Error";
    }*/
}






















