package com.pharmacy.v3.Controllers;

import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("api/cart")
@RestController
public class CartController {
    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/add-cart/{itemId}")
    public ResponseEntity<?> addToCart(@PathVariable Integer itemId, @RequestBody Cart newCart, HttpServletRequest request) {
        return cartService.addNewCartToItem(itemId, newCart, request);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cartAll")
    public List<Cart> viewMyCartItems(HttpServletRequest request) {
        return cartService.viewCartItems(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/delete-item/{itemId}")
    public ResponseEntity<?> deleteCartItemFromCart(@PathVariable Integer itemId,HttpServletRequest request) {
        return cartService.deleteItemFromCart(itemId,request);
    }

    //****
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/update-cart/{cartId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCartItem(@PathVariable Integer cartId, @RequestBody Cart uCart, HttpServletRequest request) {
        return cartService.updateCartItem(cartId, uCart);
    }
    //*****
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/cartAll/{userId}")
    public List<Cart> getAllUnPurchasedCartItems(@PathVariable Integer userId, @RequestParam boolean isPurchased, HttpServletRequest request) {
        return cartService.getAllPendingCartItems(userId, isPurchased, request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/delete/{cartId}")
    public ResponseEntity<?> deleteCartId(@PathVariable Integer cartId) {
        return cartService.deleteCart(cartId);
    }

}
