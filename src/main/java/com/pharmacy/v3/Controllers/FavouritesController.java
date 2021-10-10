package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Services.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        return favouritesService.getAllFavouritesItemsByUserToken(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/item/{itemId}")
    public ResponseEntity<?> getFavouriteItem(@PathVariable Integer itemId, HttpServletRequest request) {
        return favouritesService.getFavouriteItem(itemId, request);
    }
}
