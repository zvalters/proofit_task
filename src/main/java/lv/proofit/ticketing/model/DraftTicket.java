package lv.proofit.ticketing.model;

import lombok.Value;
import lv.proofit.ticketing.enums.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Value
public class DraftTicket {

    BigDecimal total;
    Currency currency = Currency.EUR;
    List<Item> items;

    public DraftTicket(BigDecimal total, List<Item> items) {
        this.total = total.setScale(2, RoundingMode.HALF_UP);
        this.items = items;
    }
}
