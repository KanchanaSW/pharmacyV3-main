package com.pharmacy.v3.DTO;

import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class InquiryDTO {
    private Integer inquiryId;

    private User user;
    private Item item;

    private String question;
    private String answer;
    private String date;
    private boolean isReplied;

    public InquiryDTO() {
    }

    public InquiryDTO(User user, Item item, String question, String answer, String date, boolean isReplied) {
        this.user = user;
        this.item = item;
        this.question = question;
        this.answer = answer;
        this.date = date;
        this.isReplied = isReplied;
    }

    public Integer getInquiryId() {
        return inquiryId;
    }

    public void setInquiryId(Integer inquiryId) {
        this.inquiryId = inquiryId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isReplied() {
        return isReplied;
    }

    public void setReplied(boolean replied) {
        isReplied = replied;
    }
}
