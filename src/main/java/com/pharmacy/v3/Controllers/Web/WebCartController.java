package com.pharmacy.v3.Controllers.Web;

import com.pharmacy.v3.DTO.CartDTO;
import com.pharmacy.v3.DTO.ItemDTO;
import com.pharmacy.v3.Models.Cart;
import com.pharmacy.v3.Models.Item;
import com.pharmacy.v3.Services.CartService;
import com.pharmacy.v3.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class WebCartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;


    //redirect to add to cart page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/Add2CartViewItem/{itemId}")
    public String viewAnItem(@PathVariable(name = "itemId")Integer itemId, Model model){
        try{
            ItemDTO item= itemService.getItemById(itemId);
            model.addAttribute("itemInfo",item);
        }catch (Exception e){
            model.addAttribute("error" ,"empty");
        }
        return "Add2CartViewItem";
    }
    //add new item to cart
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/Add2CartViewItem/{itemId}")
    public String addTOCart(@PathVariable(name = "itemId")Integer itemId, @ModelAttribute("cartItem")CartDTO cartDTO, HttpServletRequest request, Model model){
       try {
           ResponseEntity<?> a2c = cartService.addNewCartToItem(itemId, cartDTO, request);
           if (a2c.getStatusCodeValue() == 200) {
               model.addAttribute("success", "added success");
           } else {
               model.addAttribute("error", "added failed");
           }
           return "redirect:/ViewAllItems";
       }catch (Exception e){
           model.addAttribute("error", "added failed");
           return "redirect:/Error404";
       }
    }

    //view items in the cart
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/ViewCartItems")
    public String viewCartItems(HttpServletRequest request,Model model){
        try {
            List<Cart> list=cartService.viewCartItems(request);
            model.addAttribute("info",list);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "ViewCartItems";
    }
    //delete item from cart
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/DeleteCartItem/{itemId}")
    public String deleteCartItem(@PathVariable(name = "itemId")Integer itemId,HttpServletRequest request,Model model){
        ResponseEntity<?> isD=cartService.deleteItemFromCart(itemId,request);
        if (isD.getStatusCodeValue()==200){
            model.addAttribute("success","deleted Success");
        }else if (isD.getStatusCodeValue()==422){
            model.addAttribute("error","unable to delete");
        }else{
            model.addAttribute("error","error");
        }
        return "redirect:/ViewCartItems";
    }
    //redirecting to update cart page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/UpdateCartPage/{cartId}")
    public String updateCartPage(@PathVariable(name = "cartId")Integer cartId,Model model){
        try {
            Cart cart = cartService.getCartFromId(cartId);
            ItemDTO item= itemService.getItemById(cart.getItem().getItemId());

            model.addAttribute("itemInfo",item);
            model.addAttribute("cartInfo", cart);
            model.addAttribute("updateInfo", new CartDTO());
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "UpdateCart";
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/UpdateCart")
    public String updateCart(@ModelAttribute("updateInfo")CartDTO cartDTO,Model model){
        try{
            //Cart cart = cartService.getCartFromId(cartDTO.getCartId());

            ResponseEntity<?> cart=cartService.updateCartItem(cartDTO.getCartId(),cartDTO);
            if (cart.getStatusCodeValue()==200){
                model.addAttribute("success","updated success");
                return "redirect:/ViewCartItems";
            }else if (cart.getStatusCodeValue()==422){
                System.out.println("////////////////////////////");
                System.out.println(cart);
                model.addAttribute("error","unable to update");
                return "redirect:/Error404";
            }else{
                System.out.println("////////////////////////////");
                System.out.println(cart);
                model.addAttribute("error","error");
                return "redirect:/Error404";
            }
        }catch (Exception e){
            System.out.println("////////////////////////////");
            System.out.println(e);
            model.addAttribute("error","error e");
            return "redirect:/Error404";
        }

    }
    //display users cart items pending
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping(value = "/CartAll")
    public String viewAllUnPurchsedCartItems(HttpServletRequest request,Model model){

        List<Cart> list =cartService.getAllPendingCartItems(false, request);
        if (list.isEmpty()){
            model.addAttribute("error","unable to update");
        }else{
            model.addAttribute("list",list);
        }
        return "ViewCartItems";
    }

    //delete whole cart
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @RequestMapping(value = "/DeleteCart/{cartId}")
    public String deleteCart(@PathVariable(name = "cartId")Integer cartId,Model model){
        ResponseEntity<?> dC=cartService.deleteCart(cartId);
        if (dC.getStatusCodeValue()==200){
            model.addAttribute("success","cart deleted success");
        }else if (dC.getStatusCodeValue()==422){
            model.addAttribute("error","unable to delete cart");
        }else{
            model.addAttribute("error","error");
        }
        return "ViewCartItems";
    }
}


















