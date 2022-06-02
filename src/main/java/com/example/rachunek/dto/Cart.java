package com.example.rachunek.dto;

import com.example.rachunek.model.ProductCategory;
import com.example.rachunek.model.TaxValues;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Cart {

    private List<CartProduct> products;

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    public Cart() {
        products = new ArrayList<>();
    }

    public BigDecimal getTotalPrice(){
        return products
            .stream()
            .map(cartProduct -> cartProduct.getGrossPrice().multiply(new BigDecimal(cartProduct.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountedPrice(){
        return products
            .stream()
            .map(CartProduct::getDiscountedPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTax(String tax){

        return products
            .stream()
            .filter(cartProduct -> Objects.equals(cartProduct.getTax().getValue(), new BigDecimal(tax)))
            .map(cartProduct -> cartProduct.getDiscountedPrice()
                .subtract(cartProduct.getDiscountedPrice().divide(BigDecimal.valueOf(1 + Double.parseDouble(tax) / 100), RoundingMode.UP)
                    .setScale(2, RoundingMode.HALF_UP)
            ))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


//    public long countProductsByCategory(ProductCategory category){
//        Optional<List<CartProduct>> productList = Optional.ofNullable(products.get(category));
//        return productList.map(cartProducts -> cartProducts
//            .stream()
//            .map(CartProduct::getQuantity)
//            .count()).orElse(0L);
//    }
}
