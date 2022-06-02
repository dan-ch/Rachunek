package com.example.rachunek.dto;

import com.example.rachunek.model.ProductCategory;
import com.example.rachunek.model.TaxValues;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartProduct {

    private Long id;

    private String name;

    private ProductCategory category;

    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0")
    private BigDecimal netPrice;

    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0")
    private BigDecimal grossPrice;

    private Integer quantity;

    private int billedQuantity;

    private TaxValues tax;

    private List<Discount> discounts;

    public CartProduct(Long id, String name, ProductCategory category, BigDecimal netPrice, BigDecimal grossPrice, TaxValues tax) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
        this.quantity = 1;
        this.tax = tax;
        this.discounts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TaxValues getTax() {
        return tax;
    }

    public void setTax(TaxValues tax) {
        this.tax = tax;
    }

    public int getBilledQuantity() {
        return billedQuantity;
    }

    public void setBilledQuantity(int billedQuantity) {
        this.billedQuantity = billedQuantity;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getDiscount() {
        return discounts
            .stream()
            .map(Discount::getValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getDiscountedPrice(){
        return grossPrice.multiply(new BigDecimal(quantity)).subtract(getDiscount());
    }
}
