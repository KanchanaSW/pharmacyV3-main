package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.Models.Favourites;
import com.pharmacy.v3.Services.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebFavouritesController {
    @Autowired
    private FavouritesService favouritesService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/AddFav/{itemId}")
    public String addToFav(@PathVariable(name = "itemId")Integer itemId, HttpServletRequest request, Model model){
        try {
            ResponseEntity<?> like = favouritesService.onFavouritesItem(itemId, request);
            if (like.getStatusCodeValue() == 422) {
                model.addAttribute("success", "unliked");
            } else {
                model.addAttribute("success", "liked");
            }
        }catch (Exception e){
            model.addAttribute("error","notfound");
        }
        return "ViewAllItems";
    }
    //users favourites
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/FavAll")
    public String myFavs(HttpServletRequest request,Model model){
        try {
            List<Favourites> list = favouritesService.getAllFavouritesItemsByUserToken(request);
            if (list.isEmpty()) {
                model.addAttribute("error","empty");
            } else {
                model.addAttribute("list",list);
            }
        }catch (Exception e){
            model.addAttribute("error","notfound");
        }
        return "ViewMyFavourites";
    }
    //get m fav item
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/favItem/{itemId}")
    public String viewMyFavItem(@PathVariable(name = "itemId")Integer itemId,HttpServletRequest request,Model model){
        try {
            Favourites fav = favouritesService.getFavouriteItem(itemId, request);
            model.addAttribute("fav",fav);
        }catch (Exception e){
            model.addAttribute("error","error");
        }
        return "ViewFavItem";
    }
}
