package lv.proofit.ticketing.enums;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

public enum AgeGroup {
    ADULT(BigDecimal.ONE),
    CHILD(BigDecimal.valueOf(0.5d));

    AgeGroup(BigDecimal multiplier) {
        discountModifier = price -> price.multiply(multiplier);
    }

    private final UnaryOperator<BigDecimal> discountModifier;

    public BigDecimal applyDiscount(BigDecimal price) {
        return discountModifier.apply(price);
    }
}
