package lv.proofit.ticketing.rest.pricing.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PricingResponse {

    BigDecimal price;

}
