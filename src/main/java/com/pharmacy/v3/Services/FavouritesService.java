package com.pharmacy.v3.Services;

import com.pharmacy.v3.Models.Favourites;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.FavouritesRepository;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class FavouritesService {
    private FavouritesRepository favouritesRepository;
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Autowired
    public FavouritesService(FavouritesRepository favouritesRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.favouritesRepository = favouritesRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> onFavouritesItem(Integer itemId, HttpServletRequest request) {
        try {
            Item item = itemRepository.findById(itemId).get();
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();

            if (favouritesRepository.existsByItem(item) && favouritesRepository.existsByUser(user)) {
                Favourites deleteFavourite = favouritesRepository.findByItemAndUser(item, user);
                favouritesRepository.delete(deleteFavourite);
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("Success: Removed from your Favourites"));
            } else {
                Favourites fav = new Favourites();
                fav.setItem(item);
                fav.setUser(user);
                favouritesRepository.save(fav);
                return ResponseEntity.ok().body(new MessageResponse("Success: Added to your Favourites"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Item not found"));
        }
    }

    public List<Favourites> getAllFavouritesItemsByUserToken(HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            List<Favourites> fav = favouritesRepository.findByUserOrderByFavouritesIdDesc(user);
            return fav;
        } catch (Exception e) {
            return null;
        }
    }

    public Favourites getFavouriteItem(Integer itemId, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            Item item = itemRepository.findById(itemId).get();
            if (favouritesRepository.existsByItem(item) && favouritesRepository.existsByUser(user)) {
                Favourites fav = favouritesRepository.findByItem(item);
                return fav;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}


