package com.example.rachunek.dto;

import com.example.rachunek.model.Product;
import com.example.rachunek.model.ProductCategory;
import com.example.rachunek.model.TaxValues;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class ProductAddDTO {

    @Column(unique = true)
    private String name;

    private ProductCategory category;

    private BigDecimal netPrice;

    private TaxValues tax;

    public Product toProduct(){
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setNetPrice(netPrice);
        product.setTax(tax);
        product.setGrossPrice(
            netPrice.add(netPrice.multiply(tax.getValue().divide(BigDecimal.valueOf(100L),  new MathContext(2))))
                .setScale(2, RoundingMode.HALF_UP)
        );
        return product;
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

    public TaxValues getTax() {
        return tax;
    }

    public void setTax(TaxValues tax) {
        this.tax = tax;
    }
}
