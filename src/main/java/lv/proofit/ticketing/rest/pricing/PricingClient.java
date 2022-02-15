package lv.proofit.ticketing.rest.pricing;

import feign.Param;
import feign.RequestLine;
import lv.proofit.ticketing.rest.pricing.dto.PricingResponse;

public interface PricingClient {

    @RequestLine("GET /pricing/{terminal}")
    PricingResponse getBasePrice(@Param("terminal") String terminal);

}
