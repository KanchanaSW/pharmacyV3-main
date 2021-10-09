package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Services.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> addInquiryByItemId(@PathVariable Integer itemId, @RequestBody Inquiry inquiry, HttpServletRequest request) {
        return inquiryService.addInquiryByItemId(itemId, inquiry, request);
    }
    //add reply
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/answer/{inquiryId}")
    public ResponseEntity<?> addReplyByInquiryId(@PathVariable Integer inquiryId, @RequestBody Inquiry inquiry, HttpServletRequest request) {
        return inquiryService.addReplyByInquiryId(inquiryId,inquiry);
    }
   //view all Inquires by itemid
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/item-all/{itemId}")
    public ResponseEntity<?> getAllInquiryByItemId(@PathVariable Integer itemId, HttpServletRequest request) {
       return inquiryService.getAllInquiryByItemId(itemId);
    }
    //get all inquries if replied
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/replied/{isReplied}")
    public ResponseEntity<?> getAllInquiryIsReplied(@PathVariable boolean isReplied, HttpServletRequest request) {
        return inquiryService.getAllInquiryIsReplied(isReplied);
    }
    //get inquiry by id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/item/{inquiryId}")
    public ResponseEntity<?> getInquiryById(@PathVariable Integer inquiryId) {
        return inquiryService.getInquiryById(inquiryId);
    }
    //delete unwanted inquires
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{inquiryId}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Integer inquiryId, HttpServletRequest request) {
        return inquiryService.deleteInquiry(inquiryId,request);
    }

}
