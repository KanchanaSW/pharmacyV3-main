package com.pharmacy.v3.Services;


import com.pharmacy.v3.DTO.CartDTO;
import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Models.User;
import com.pharmacy.v3.Repositories.CartRepository;
import com.pharmacy.v3.Repositories.ItemRepository;
import com.pharmacy.v3.Repositories.UserRepository;
import com.pharmacy.v3.Models.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public Cart save(Cart cart){return cartRepository.save(cart);}
    public List<Cart> all(){return cartRepository.findAll();}
    public void delete(Cart cart){cartRepository.delete(cart);}
    //add new item to cart
    public ResponseEntity<?> addNewCartToItem(Integer itemId, CartDTO newCart, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
        Item item = itemRepository.findById(itemId).get();
        if (item.getQuantity() <= newCart.getQuantity()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("inventory ran out");
        }
        if (cartRepository.existsByUserAndItemAndIsPurchased(user, item, false)) {
            Cart cart = cartRepository.findByUserAndItem(user, item);
            cart.setQuantity(cart.getQuantity() + newCart.getQuantity());
            double price=item.getPrice() * newCart.getQuantity();
            cart.setTotal(cart.getTotal() + price);
            cartRepository.save(cart);

            int itemQuantity = item.getQuantity();
            item.setQuantity(itemQuantity - cart.getQuantity());//update item q
            item = itemRepository.save(item);
            return ResponseEntity.ok().body(new MessageResponse("Added to your existing cart"));
        } else {
            Cart cart = new Cart();
            cart.setItem(item);
            cart.setUser(user);
            cart.setQuantity(newCart.getQuantity());
            cart.setTotal(newCart.getTotal());
            cartRepository.save(cart);

            int itemQuantity = item.getQuantity();
            item.setQuantity(itemQuantity - cart.getQuantity());//update item q
            item = itemRepository.save(item);
            return ResponseEntity.ok().body(new MessageResponse("Success: Added to your cart"));
        }
    }
    //Update Cart Items
    public ResponseEntity<?> updateCartItem(Integer cartId,CartDTO uCart) {
        Item item = itemRepository.findById(uCart.getItemId()).get();
        Cart cart = cartRepository.findById(cartId).get();
        if (item.getQuantity() <= uCart.getQuantity()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("inventory ran out");
        }
        item.setQuantity( item.getQuantity() + cart.getQuantity());//update item q
        itemRepository.save(item);

        double price = cart.getItem().getPrice() * uCart.getQuantity();
        cart.setQuantity(uCart.getQuantity());
        cart.setTotal(uCart.getTotal());
        cartRepository.save(cart);

        item.setQuantity(item.getQuantity() - cart.getQuantity());//update item q
        itemRepository.save(item);
        return ResponseEntity.ok().body(new MessageResponse("Success: Updated Success"));
    }
    //delete whole cart by cartId
    public ResponseEntity<?> deleteCart(Integer cartId) {
        if (cartRepository.existsById(cartId)) {
            Cart cart = cartRepository.findById(cartId).get();
            cartRepository.delete(cart);
            Item item = itemRepository.findById(cart.getItem().getItemId()).get();
            int itemQuantity = item.getQuantity();
            item.setQuantity(itemQuantity + cart.getQuantity());//update item q
            item = itemRepository.save(item);

            return ResponseEntity.ok().body(new MessageResponse("Success: Cart deleted."));
        } else {
            return ResponseEntity.ok().body(new MessageResponse("Error: cart not available"));
        }
    }

    public Cart getCartFromId(Integer cartId){
        Optional<Cart> cart=cartRepository.findById(cartId);
        Cart c=null;
        if (cart.isPresent()){
            c=cart.get();
        }
        return c;
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
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: item not available"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
    //display cart list
    public List<Cart> viewCartItems(HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            List<Cart> cartList = cartRepository.findByUserAndIsPurchased(user, false);
            return cartList;
        } catch (Exception e) {
            return null;
        }
    }
    //get all pending cart items
    public List<Cart> getAllPendingCartItems( boolean isPurchased, HttpServletRequest request) {
        try {
            User u = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            User user = userRepository.findById(u.getUserId()).get();
            List<Cart> pendingCartList = cartRepository.findByUserAndIsPurchased(user, isPurchased);
            return pendingCartList;
        } catch (Exception e) {
            return null;
        }
    }
    public List<Cart> getUserCart(User user) {
        try {
         //   User user=userRepository.findById(userId).get();
            List<Cart> cartList = cartRepository.findByUserAndIsPurchased(user, false);
            return cartList;
        } catch (Exception e) {
            return null;
        }
    }
/*
    //get cart count for user
    public List<CartDTO> getCartCountByUser(){
        List<Cart> cartList=cartRepository.findAll();
        for (int x=0;x<cartList.size();x++){
            User user=cartList.get(x).getUser();
            int cartCount=cartRepository.countCartsByUser(user);
        }
    }*/

/*    //add new item to cart
    public ResponseEntity<?> addNewCartToItem(Integer itemId, CartDTO newCart, HttpServletRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUserPrincipal().getName()).get();
            Item item = itemRepository.findById(itemId).get();
            int qI=item.getQuantity();
            int newQ=newCart.getQuantity();
            int calc=qI-newQ;
            if (cartRepository.existsByUserAndItemAndIsPurchased(user, item, false)) {
                Cart cart = cartRepository.findByUserAndItem(user, item);
                cart.setQuantity(cart.getQuantity() + newCart.getQuantity());
                double price = item.getPrice() * newCart.getQuantity();
                cart.setTotal(cart.getTotal() + price);
                cartRepository.save(cart);
               //update item
               itemService.updateQuantity(itemId,calc);
                return ResponseEntity.ok().body(new MessageResponse("Added to your existing cart"));
            } else {
                Cart cart = new Cart();
                cart.setItem(item);
                cart.setUser(user);
                cart.setQuantity(newCart.getQuantity());
                cart.setTotal(newCart.getTotal());
                cartRepository.save(cart);
                //update item
                itemService.updateQuantity(itemId,calc);

                return ResponseEntity.ok().body(new MessageResponse("Success: Added to your cart"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }
     //fix this
    //Update Cart Items
    public ResponseEntity<?> updateCartItem(Integer cartId, CartDTO uCart) {
        try {
            Item item = itemRepository.findById(uCart.getItemId()).get();
            int qI=item.getQuantity();
            int newQ=uCart.getQuantity();

            if (cartRepository.existsById(cartId)) {
                Cart cart = cartRepository.findById(cartId).get();
                int oldQ=cart.getQuantity();
                int dif= (oldQ - newQ);

                cart.setQuantity(uCart.getQuantity());
                cart.setTotal(uCart.getTotal());
                cartRepository.save(cart);
                int newQI= (qI+dif);
                System.out.println("//////////////////////////////////////////////////");
                System.out.println(newQI);

                itemService.updateQuantity(item.getItemId(),newQI);
                return ResponseEntity.ok().body(new MessageResponse("Success: Updated Success"));
            }
            return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: updated un-successfull"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }*/
    /*
    //delete whole cart by cartId
    public ResponseEntity<?> deleteCart(Integer cartId) {
        try {
            if (cartRepository.existsById(cartId)) {
                Cart cart = cartRepository.findById(cartId).get();
                int quanity=cart.getQuantity();
                Item item=itemRepository.findById(cart.getItem().getItemId()).get();
                int oldQuentity=item.getQuantity();
                item.setQuantity(oldQuentity+quanity);
                itemRepository.save(item);//updating item quantity

                cartRepository.delete(cart);
                return ResponseEntity.ok().body(new MessageResponse("Success: Cart deleted."));
            } else {
                return ResponseEntity.unprocessableEntity().body(new MessageResponse("Error: cart not available"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(("Error") + e));
        }
    }*/



}
