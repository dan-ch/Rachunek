package com.example.rachunek.dto;

import com.example.rachunek.model.ProductCategory;

import java.math.BigDecimal;
import java.util.*;

public class Cart {

    private Map<ProductCategory, List<CartProduct>> products;

    private BigDecimal discount;

    public Map<ProductCategory, List<CartProduct>> getProducts() {
        return products;
    }

    public void setProducts(Map<ProductCategory, List<CartProduct>> products) {
        this.products = products;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Cart() {
        products = new HashMap<>();
        discount = new BigDecimal(0);
    }

    public BigDecimal getTotalPrice(){
        return products
            .values()
            .stream()
            .flatMap(List::stream)
            .map(cartProduct -> cartProduct.getGrossPrice().multiply(new BigDecimal(cartProduct.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountedPrice(){
        return products
            .values()
            .stream()
            .flatMap(List::stream)
            .map(cartProduct -> cartProduct.getDiscountedPrice().multiply(new BigDecimal(cartProduct.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            .subtract(discount);
    }

    public long countProductsByCategory(ProductCategory category){
        Optional<List<CartProduct>> productList = Optional.ofNullable(products.get(category));
        return productList.map(cartProducts -> cartProducts
            .stream()
            .map(CartProduct::getQuantity)
            .count()).orElse(0L);
    }
}
