package com.pharmacy.v3.Services;


import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.CartRepository;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ItemRepository itemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    //add new item to cart
    public ResponseEntity<?> addNewCartToItem(Integer itemId, Cart newCart, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            Item item = itemRepository.findById(itemId).get();
            if (cartRepository.existsByUserAndItemAndIsPurchased(user, item, false)) {
                Cart cart = cartRepository.findByUserAndItem(user, item);
                cart.setQuantity(cart.getQuantity() + newCart.getQuantity());
                double price = item.getPrice() * newCart.getQuantity();
                cart.setTotal(cart.getTotal() + price);
                cartRepository.save(cart);
                return ResponseEntity.ok().body(new MessageResponse("Added to your existing cart"));
            } else {
                Cart cart = new Cart();
                cart.setItem(item);
                cart.setUser(user);
                cart.setQuantity(newCart.getQuantity());
                cart.setTotal(newCart.getTotal());
                cartRepository.save(cart);
                return ResponseEntity.ok().body(new MessageResponse("Success: Added to your cart"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //Update Cart Items
    public ResponseEntity<?> updateCartItem(Integer cartId, Cart uCart) {
        try {
            if (cartRepository.existsById(cartId)) {
                Cart cart = cartRepository.findById(cartId).get();
                double price = cart.getItem().getPrice() * uCart.getQuantity();
                cart.setQuantity(uCart.getQuantity());
                cart.setTotal(price);
                cartRepository.save(cart);
                return ResponseEntity.ok().body(new MessageResponse("Success: Updated Success"));
            }
            return ResponseEntity.badRequest().body(new MessageResponse("Error: updated un-successfull"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //delete an item from the cart
    public ResponseEntity<?> deleteItemFromCart(Integer itemId, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            if (cartRepository.existsByItemItemIdAndUserUserId(itemId, user.getUserId())) {
                Cart cart = cartRepository.findByItemItemIdAndUserUserId(itemId, user.getUserId());
                cartRepository.delete(cart);
                return ResponseEntity.ok().body(new MessageResponse("Success: Item deleted."));
            } else {
                return ResponseEntity.ok().body(new MessageResponse("Error: item not available"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //delete whole cart by cartId
    public ResponseEntity<?> deleteCart(Integer cartId) {
        try {
            if (cartRepository.existsById(cartId)) {
                Cart cart = cartRepository.findById(cartId).get();
                cartRepository.delete(cart);
                return ResponseEntity.ok().body(new MessageResponse("Success: Cart deleted."));
            } else {
                return ResponseEntity.ok().body(new MessageResponse("Error: cart not available"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //display cart list
    public ResponseEntity<?> viewCartItems(HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            List<Cart> cartList = cartRepository.findByUserAndIsPurchased(user, false);
            return ResponseEntity.ok().body(cartList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

    //get all pending cart items
    public ResponseEntity<?> getAllPendingCartItems(Integer userId, boolean isPurchased, HttpServletRequest request) {
        try {
            User user = userRepository.findById(userId).get();
            List<Cart> pendingCartList = cartRepository.findByUserAndIsPurchased(user, isPurchased);
            return ResponseEntity.ok().body(pendingCartList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }

}
