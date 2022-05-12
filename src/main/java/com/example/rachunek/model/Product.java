package com.example.rachunek.model;

import com.example.rachunek.dto.CartProduct;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0")
    private BigDecimal netPrice;

    @Digits(integer = 5, fraction = 2)
    @DecimalMin(value = "0.0")
    private BigDecimal grossPrice;

    @Enumerated(EnumType.STRING)
    private TaxValues tax;

    public Product() {
    }

    public CartProduct toCartProduct(){
        return new CartProduct(id, name, category, netPrice, grossPrice, tax);
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

    public TaxValues getTax() {
        return tax;
    }

    public void setTax(TaxValues tax) {
        this.tax = tax;
    }
}
