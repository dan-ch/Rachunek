package com.example.rachunek.controller;

import com.example.rachunek.dto.Cart;
import com.example.rachunek.service.CartService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    private final CartService cartService;
    private final KieContainer kieContainer;

    public CartController(CartService cartService, KieContainer kieContainer) {
        this.cartService = cartService;
        this.kieContainer = kieContainer;
    }

    @GetMapping
    public String cartProducts(Model model, @ModelAttribute("cart") Cart cart){
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/addProduct")
    public String addProductToCart(@ModelAttribute("cart") Cart cart, @RequestParam Long id){
        cartService.addProductToCart(cart, id);
        return "redirect:/product";
    }

    @GetMapping("/removeProduct")
    public String removeProductFromCart(@ModelAttribute("cart") Cart cart, @RequestParam Long id){
        cartService.removeProductFromCart(cart, id);
        return "redirect:/cart";
    }

    @GetMapping("/countDiscount")
    public String removeProductFromCart(@ModelAttribute("cart") Cart cart){
        KieSession session = kieContainer.newKieSession();
        session.insert(cart);
        session.fireAllRules();
        return "cart";
    }

    @ModelAttribute("cart")
    public Cart cart(){
        return new Cart();
    }
}
