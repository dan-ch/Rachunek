package com.example.rachunek.model;

import java.math.BigDecimal;

public enum TaxValues {
    ZERO(BigDecimal.valueOf(0)),
    FIVE(BigDecimal.valueOf(5)),
    EIGHT(BigDecimal.valueOf(8)),
    TWENTY_THREE(BigDecimal.valueOf(23));

    public final BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    TaxValues(BigDecimal value) {
        this.value = value;
    }
}
