package lv.proofit.ticketing.rest.pricing;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import lv.proofit.ticketing.rest.RestProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class PricingConfiguration {

    @Bean
    public PricingClient pricingClient(RestProperties props, ObjectMapper mapper) {
        return Feign.builder()
                .decoder(new JacksonDecoder(mapper))
                .target(PricingClient.class, props.getPricingUri().toString());
    }
}
