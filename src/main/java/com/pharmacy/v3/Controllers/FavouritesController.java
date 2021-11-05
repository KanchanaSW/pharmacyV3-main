package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Models.Favourites;
import com.pharmacy.v3.Services.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/fav")
@RestController
public class FavouritesController {
    private FavouritesService favouritesService;

    @Autowired
    public FavouritesController(FavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-fav/{itemId}")
    public ResponseEntity<?> addFavourites(@PathVariable Integer itemId, HttpServletRequest request) {
        return favouritesService.onFavouritesItem(itemId, request);
    }

    //favourites page displaying all favourite item of a user
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/favAll")
    public ResponseEntity<?> myFavourites(HttpServletRequest request) {
        try {
            List<Favourites> list = favouritesService.getAllFavouritesItemsByUserToken(request);
            if (list.isEmpty()) {
                return ResponseEntity.ok().body("Empty");
            } else {
                return ResponseEntity.ok().body(list);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }
//get my fav item
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/item/{itemId}")
    public ResponseEntity<?> getFavouriteItem(@PathVariable Integer itemId, HttpServletRequest request) {
        try {
            Favourites fav = favouritesService.getFavouriteItem(itemId, request);
            return ResponseEntity.ok().body(fav);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }
}
