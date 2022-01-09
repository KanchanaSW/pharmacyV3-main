package com.pharmacy.v3.Services;

import com.pharmacy.v3.DTO.InquiryDTO;
import com.pharmacy.v3.Models.Inquiry;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.InquiryRepository;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Models.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Inquiry save(Inquiry inquiry){return inquiryRepository.save(inquiry);}
    public void delete(Inquiry inquiry){inquiryRepository.delete(inquiry);}

    public ResponseEntity<?> addInquiryByItemId(Integer itemId, InquiryDTO newInquiry, HttpServletRequest request) {
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

    public List<Inquiry> getAllInquiryByItemId(Integer itemId) {
        List<Inquiry> list = inquiryRepository.findByItemItemId(itemId);
        List<Inquiry> list2=null;
        if (! list.isEmpty()){
            list2=list;
        }
        return list2;
    }

    public List<Inquiry> getAllInquiryIsReplied(boolean isReplied) {
        List<Inquiry> list = inquiryRepository.findByIsReplied(isReplied);
        List<Inquiry> list2=null;
        if (! list.isEmpty()){
            list2=list;
        }
        return list2;
    }

    public ResponseEntity<?> addReplyByInquiryId(Integer inquiryId, InquiryDTO reply) {
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

    public Inquiry getInquiryById(Integer inquiryId) {
        Optional<Inquiry> info=inquiryRepository.findById(inquiryId);
        Inquiry iq=null;
        if (info.isPresent()){
            iq=info.get();
        }
        return iq;
        /*
        try {
            Inquiry inquiry=inquiryRepository.findById(inquiryId).get();
            return ResponseEntity.ok().body(inquiry);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("None found "));
        }*/
    }

    public ResponseEntity<?> deleteInquiry(Integer inquiryId) {
        try {
            if (inquiryRepository.existsById(inquiryId)){
                inquiryRepository.deleteById(inquiryId);
                return ResponseEntity.ok().body(new MessageResponse("Success: deleted inquiry"));
            }else {
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: Inquiry dont exists "));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("Error "));
        }
    }

    public List<Inquiry> allInquires(){
        try {
            return inquiryRepository.findAll();
        }catch (Exception e){
            return null;
        }
    }
    public List<Inquiry> myInquires(Authentication authentication){
        try {
            User user=userRepository.findUserByUsername(authentication.getName());
            return inquiryRepository.findByUserUserId(user.getUserId());
        }catch (Exception e){
            return null;
        }
    }
}
