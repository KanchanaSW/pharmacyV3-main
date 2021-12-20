package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.InquiryDTO;
import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Services.InquiryService;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebInquiryController {
    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private ItemService itemService;

    //redirect to add inquiry page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/AddInquiryPage")
    public String addInquiryPage(Model model){
        List<ItemDTO> list=itemService.getAllItems();
        model.addAttribute("list",list);
        model.addAttribute("AddInquiry",new InquiryDTO());
        return "AddInquiry";
    }
    //add Inquiry
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping("/AddInquiry")
    public String addInquiry(@ModelAttribute("AddInquiry")InquiryDTO inquiryDTO, HttpServletRequest request, Model model){
       ResponseEntity<?> inquiry= inquiryService.addInquiryByItemId(inquiryDTO.getItemId(), inquiryDTO,request);
       if (inquiry.getStatusCodeValue()==200) {
           model.addAttribute("success", "inquiry added");
       }else{
           model.addAttribute("error","error occured");
       }
       return "/AddInquiry";
    }

    //redirecting to add inquiry page
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/InquiryReplyPage/{inquiryId}")
    public String inquiryAnswerPage(@PathVariable(name = "inquiryId")Integer inquiryId,Model model){
        Inquiry inquiry=inquiryService.getInquiryById(inquiryId);
        if (inquiry != null) {
            model.addAttribute("inquiry", inquiry);
            model.addAttribute("InquiryReply", new InquiryDTO());
        }else{
            model.addAttribute("error","empty");
        }
        return "InquiryReply";
    }
    //add reply
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/InquiryReply")
    public String replyInquiry(@ModelAttribute("InquiryReply")InquiryDTO inquiryDTO,Model model){
        try {
            ResponseEntity<?> reply = inquiryService.addReplyByInquiryId(inquiryDTO.getInquiryId(), inquiryDTO);
            if (reply.getStatusCodeValue() == 200) {
                model.addAttribute("success", "reply added success");
            } else {
                model.addAttribute("error", "error happen");
            }
        }catch (Exception e){
            model.addAttribute("error","error happen");
        }
        return "/InquiryReply";
    }

    //View all inquiries about an item
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/InquiryAllItem/{itemId}")
    public String viewAllInquiresOnItem(@PathVariable(name = "itemId")Integer itemId,Model model){
        List<Inquiry> list=inquiryService.getAllInquiryByItemId(itemId);
        if (! list.isEmpty()) {
            model.addAttribute("inquires", list);
        }else {
            model.addAttribute("error","empty");
        }
        return "ViewItemInquires";
    }

    //view all the replied inquires
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/InquiryReplied")
    public String viewAllReplied(Model model){
       // boolean isReplied=true;
        List<Inquiry> list=inquiryService.getAllInquiryIsReplied(true);
        if (list.isEmpty()){
            model.addAttribute("replied",list);
        }else {
            model.addAttribute("error","Empty");
        }
        return "InquiresReplied";
    }
    //view an inquiry
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/Inquiry/{inquiryId}")
    public String viewInquiry(@PathVariable(name = "inquiryId")Integer inquiryId,Model model){
       try {
           Inquiry inquiry = inquiryService.getInquiryById(inquiryId);
           model.addAttribute("info", inquiry);
       }catch (Exception e){
           model.addAttribute("error","empty");
       }
       return "ViewInquiry";
    }

    //detete an inquiry
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/DeleteInquiry/{inquiryId}")
    public String deleteInquiry(@PathVariable(name = "inquiryId")Integer inquiryId,Model model){
        try {
            ResponseEntity<?> dI = inquiryService.deleteInquiry(inquiryId);
            if (dI.getStatusCodeValue() == 422) {
                model.addAttribute("error", "Empty");
            } else {
                model.addAttribute("info", dI);
            }
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "ViewAllInquires";
    }

    //view all inquires
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/ViewAllInquires")
    public String viewAll(Model model){
        List<Inquiry> all=inquiryService.allInquires();
        if (all.isEmpty()){
            model.addAttribute("error","Empty");
        }else{
            model.addAttribute("list",all);
        }
        return "ViewAllInquires";
    }

    //view my inquires
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping("/ViewMyInquiry")
    public String viewMyInquires(Authentication authentication,Model model){
        try {
            List<Inquiry> list=inquiryService.myInquires(authentication);
            if (list.isEmpty()) {
                model.addAttribute("error","empty");
            } else {
                model.addAttribute("list",list);
            }
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "ViewMyInquires";
    }
}














