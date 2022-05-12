package com.example.rachunek.service;

import com.example.rachunek.dto.Cart;
import com.example.rachunek.dto.CartProduct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addProductToCart(Cart cart, Long productId){
        CartProduct product = productService.getProductById(productId).toCartProduct();
        if(cart.getProducts().containsKey(product.getCategory())){
            cart.getProducts().get(product.getCategory())
                .stream()
                .filter(cartProduct -> cartProduct.getId().equals(productId))
                .findAny()
                .ifPresentOrElse(cartProduct -> cartProduct.setQuantity(cartProduct.getQuantity() + 1),
                    () -> cart.getProducts().get(product.getCategory()).add(product));
        } else {
            cart.getProducts().put(product.getCategory(), new ArrayList<>(List.of(product)));
        }

    }

    public void removeProductFromCart(Cart cart, Long productId) {
        CartProduct product = productService.getProductById(productId).toCartProduct();
        CartProduct cartProduct = cart.getProducts().get(product.getCategory())
            .stream()
            .filter(item -> item.getId().equals(productId))
            .findAny()
            .get();

        cart.getProducts().get(product.getCategory()).remove(cartProduct);

        if (!cartProduct.getQuantity().equals(1)){
            cartProduct.setQuantity(cartProduct.getQuantity() - 1);
            cart.getProducts().get(product.getCategory()).add(cartProduct);
        }
        removeZeroProductsCategories(cart);
    }

    private void removeZeroProductsCategories(Cart cart) {
        cart.setProducts(cart.getProducts()
            .entrySet()
            .stream()
            .filter(productCategoryListEntry -> productCategoryListEntry.getValue().size() > 0)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
            )
        );
    }
}
