package com.example.rachunek.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Discount {

    private String name;
    private BigDecimal value;

    public Discount(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
