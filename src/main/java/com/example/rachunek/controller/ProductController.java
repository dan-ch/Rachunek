package com.example.rachunek.controller;

import com.example.rachunek.dto.CartProduct;
import com.example.rachunek.dto.ProductAddDTO;
import com.example.rachunek.model.Product;
import com.example.rachunek.model.ProductCategory;
import com.example.rachunek.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/product")
@SessionAttributes("cart")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String products(Model model, @RequestParam Optional<String> name){
        name.ifPresentOrElse(passedName -> {
            model.addAttribute("products", productService.getByProductName(passedName));
        },
            () -> model.addAttribute("products", productService.getAllProducts()));
        return "products";
    }

    @GetMapping("/add")
    public String categories(Model model){
        model.addAttribute("product", new ProductAddDTO());
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(ProductAddDTO product){
        productService.addProduct(product);
        return "redirect:/product";
    }

}
