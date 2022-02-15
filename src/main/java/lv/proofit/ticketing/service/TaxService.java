package lv.proofit.ticketing.service;

import lombok.RequiredArgsConstructor;
import lv.proofit.ticketing.rest.taxing.TaxingClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxingClient taxingClient;

    public BigDecimal applyTax(BigDecimal price) {
        List<BigDecimal> taxRates = taxingClient.getCurrentTaxes().getTaxRates();
        return price.multiply(taxRates.get(0).add(BigDecimal.ONE));
    }
}
