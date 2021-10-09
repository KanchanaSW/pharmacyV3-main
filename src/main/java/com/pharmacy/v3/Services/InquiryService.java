package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.InquiryRepository;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class InquiryService {
    private InquiryRepository inquiryRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Autowired
    public InquiryService(InquiryRepository inquiryRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.inquiryRepository = inquiryRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> addInquiryByItemId(Integer itemId, Inquiry newInquiry, HttpServletRequest request) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String date = sdf.format(new Date());

            Item item = itemRepository.findById(itemId).get();
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            Inquiry inquiry = new Inquiry();
            inquiry.setDate(date);//system date added
            inquiry.setReplied(false);
            inquiry.setItem(item);
            inquiry.setUser(user);
            inquiry.setQuestion(newInquiry.getQuestion());
            inquiryRepository.save(inquiry);
            List<Inquiry> list = inquiryRepository.findByItemItemId(itemId);

            return ResponseEntity.ok().body(new MessageResponse("Success: Inquiry added"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: "));
        }
    }

    public ResponseEntity<?> getAllInquiryByItemId(Integer itemId) {
        try {
            List<Inquiry> list = inquiryRepository.findByItemItemId(itemId);
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("None found "));
        }
    }

    public ResponseEntity<?> getAllInquiryIsReplied(boolean isReplied) {
        try {
            List<Inquiry> list = inquiryRepository.findByIsReplied(isReplied);
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("None found "));
        }
    }

    public ResponseEntity<?> addReplyByInquiryId(Integer inquiryId, Inquiry reply) {
        try {
            if (inquiryRepository.existsById(inquiryId)) {
                Inquiry inquiry = inquiryRepository.findById(inquiryId).get();
                inquiry.setAnswer(reply.getAnswer());
                inquiry.setReplied(true);
                inquiryRepository.save(inquiry);
                return ResponseEntity.ok().body(new MessageResponse("Success: Replied to the inquiry"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Inquiry is not available"));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: "));
        }
    }

    public ResponseEntity<?> getInquiryById(Integer inquiryId) {
        try {
            Inquiry inquiry=inquiryRepository.findById(inquiryId).get();
            return ResponseEntity.ok().body(inquiry);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("None found "));
        }
    }

    public ResponseEntity<?> deleteInquiry(Integer inquiryId,HttpServletRequest request) {
        try {
            if (inquiryRepository.existsById(inquiryId)){
                inquiryRepository.deleteById(inquiryId);
                return ResponseEntity.ok().body(new MessageResponse("Success: deleted inquiry"));
            }else {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Inquiry dont exists "));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error "));
        }
    }
}
