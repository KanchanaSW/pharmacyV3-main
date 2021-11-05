package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.DTO.InquiryDTO;
import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Services.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/inquiry")
@RestController
public class InquiryController {
    private InquiryService inquiryService;

    @Autowired
    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    //add Inquiry
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/item/{itemId}")
    public ResponseEntity<?> addInquiryByItemId(@PathVariable Integer itemId, @RequestBody InquiryDTO inquiry, HttpServletRequest request) {
        return inquiryService.addInquiryByItemId(itemId, inquiry, request);
    }

    //add reply
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/answer/{inquiryId}")
    public ResponseEntity<?> addReplyByInquiryId(@PathVariable Integer inquiryId, @RequestBody InquiryDTO inquiry, HttpServletRequest request) {
        return inquiryService.addReplyByInquiryId(inquiryId, inquiry);
    }

    //view all Inquires about an Item
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/item-all/{itemId}")
    public ResponseEntity<?> getAllInquiryByItemId(@PathVariable Integer itemId) {
        List<Inquiry> list=inquiryService.getAllInquiryByItemId(itemId);
        if (list.isEmpty()){
            return ResponseEntity.badRequest().body("empty");
        }else {
            return ResponseEntity.ok(list);
        }
    }

    //get all inquries if replied
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/replied/{isReplied}")
    public ResponseEntity<?> getAllInquiryIsReplied(@PathVariable boolean isReplied, HttpServletRequest request) {
        List<Inquiry> list=inquiryService.getAllInquiryIsReplied(isReplied);
        if (list.isEmpty()){
            return ResponseEntity.badRequest().body("empty");
        }else {
            return ResponseEntity.ok(list);
        }
    }

    //get inquiry by id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/item/{inquiryId}")
    public ResponseEntity<?> getInquiryById(@PathVariable Integer inquiryId) {
        Inquiry inquiry=inquiryService.getInquiryById(inquiryId);
        return ResponseEntity.ok(inquiry);
    }

    //delete unwanted inquires
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{inquiryId}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Integer inquiryId) {
        return inquiryService.deleteInquiry(inquiryId);
    }

    //view all inquires
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/ViewAllInquires")
    public List<Inquiry> viewAllInquires(){
        return inquiryService.allInquires();
    }

    //view my inquires
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping("/ViewMyInquires")
    public ResponseEntity<?> viewMyInquires(Authentication authentication){
      try {
          List<Inquiry> list = inquiryService.myInquires(authentication);
          if (list.isEmpty()) {
              return ResponseEntity.badRequest().body("empty");
          } else {
              return ResponseEntity.ok(list);
          }
      }catch (Exception e){
          return ResponseEntity.badRequest().body("error");
      }
    }
}
