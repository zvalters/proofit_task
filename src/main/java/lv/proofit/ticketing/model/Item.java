package lv.proofit.ticketing.model;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class Item {

    String description;
    BigDecimal cost;

    public Item(String description, BigDecimal cost) {
        this.description = description;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
    }
}
