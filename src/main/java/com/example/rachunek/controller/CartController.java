package com.example.rachunek.controller;

import com.example.rachunek.dto.Cart;
import com.example.rachunek.dto.CartProduct;
import com.example.rachunek.service.CartService;
import com.example.rachunek.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_PDF;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    private final CartService cartService;
    private final KieContainer kieContainer;
    private final PdfService pdfService;

    public CartController(CartService cartService, KieContainer kieContainer, PdfService pdfService) {
        this.cartService = cartService;
        this.kieContainer = kieContainer;
        this.pdfService = pdfService;
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

    @GetMapping("/bill")
    public String getBill(@ModelAttribute("cart") Cart cart){
        return "bill";
    }


    @GetMapping(value = "/bill/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> noteReport(@ModelAttribute("cart") Cart cart) throws DocumentException,
        IOException {
            String html = pdfService.parseBillTemplate(cart);
            ByteArrayInputStream bis = pdfService.generatePdfFromHtml(html);
            bis.close();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=rachunek.pdf");
            return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }



    @GetMapping("/countDiscount")
    public String removeProductFromCart(@ModelAttribute("cart") Cart cart){
        KieSession session = kieContainer.newKieSession();
        for (List<CartProduct> productsList : cart.getProducts().values()){
            for (CartProduct product : productsList) {
                session.insert(product);
            }
        }
        session.insert(cart);
        session.fireAllRules();
        return "cart";
    }

    @ModelAttribute("cart")
    public Cart cart(){
        return new Cart();
    }
}
