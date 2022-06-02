package com.example.rachunek.service;

import com.example.rachunek.dto.Cart;
import com.example.rachunek.dto.CartProduct;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class CartService {

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addProductToCart(Cart cart, Long productId){
        CartProduct product = productService.getProductById(productId).toCartProduct();

        cart.getProducts()
            .stream()
            .filter(cartProduct -> cartProduct.getId().equals(productId))
            .findAny()
            .ifPresentOrElse(cartProduct -> cartProduct.setQuantity(cartProduct.getQuantity() + 1),
                () -> cart.getProducts().add(product));
    }

    public void removeProductFromCart(Cart cart, Long productId) {
        cart.setProducts(
            cart.getProducts()
                .stream()
                .filter(item -> !item.getId().equals(productId))
                .collect(Collectors.toList())

        );
    }
}
