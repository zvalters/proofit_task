package lv.proofit.ticketing.rest.taxing;

import feign.RequestLine;
import lv.proofit.ticketing.rest.taxing.dto.TaxResponse;

public interface TaxingClient {

    @RequestLine("GET /taxes")
    TaxResponse getCurrentTaxes();

}
