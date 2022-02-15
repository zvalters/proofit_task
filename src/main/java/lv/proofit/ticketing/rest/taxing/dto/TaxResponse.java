package lv.proofit.ticketing.rest.taxing.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TaxResponse {

    List<BigDecimal> taxRates;

}
