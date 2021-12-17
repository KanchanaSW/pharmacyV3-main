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
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    //redirect to add to cart page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
    @PostMapping(value = "/Add2CartViewItem/{itemId}")
    public String addTOCart(@PathVariable(name = "itemId")Integer itemId, @ModelAttribute("cartItem") CartDTO cartDTO, HttpServletRequest request, Model model){
        try {
            ResponseEntity<?> a2c = cartService.addNewCartToItem(itemId, cartDTO, request);
            if (a2c.getStatusCodeValue() == 200) {
                model.addAttribute("success", "added success");
            } else {
                model.addAttribute("error", "added failed");
            }
            return "redirect:/CartListAndItems";
        }catch (Exception e){
            model.addAttribute("status", e);
            return "redirect:/Error404";
        }
    }


    //view items in the cart and all items
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
    @GetMapping(value = "/CartListAndItems")
    public String viewCartAndItems(HttpServletRequest request,Model model){
        try {
            List<ItemDTO> allItems = itemService.getAllItems();
            model.addAttribute("info", allItems);

            List<Cart> list=cartService.viewCartItems(request);
            model.addAttribute("cItem",list);
        }catch (Exception e){
            model.addAttribute("error","empty");
        }
        return "CartView";
    }


    //redirecting to update cart page
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
    @RequestMapping(value = "/UpdateCart")
    public String updateCart(@ModelAttribute("updateInfo")CartDTO cartDTO,Model model){
        try{
            //Cart cart = cartService.getCartFromId(cartDTO.getCartId());
            ResponseEntity<?> cart=cartService.updateCartItem(cartDTO.getCartId(),cartDTO);
            if (cart.getStatusCodeValue()==200){
                model.addAttribute("success","updated success");
                return "redirect:/CartListAndItems";
            }else if (cart.getStatusCodeValue()==422){
                model.addAttribute("error","unable to update");
                return "redirect:/Error404";
            }else{
                model.addAttribute("error","error");
                return "redirect:/Error404";
            }

        }catch (Exception e){
            model.addAttribute("error","error e");
            return "redirect:/Error404";
        }
    }

    //delete  cart item
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('CUSTOMER')")
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
        return "redirect:/CartListAndItems";
    }}


















