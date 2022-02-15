package lv.proofit.ticketing.rest.taxing;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import lv.proofit.ticketing.rest.RestProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TaxingConfiguration {

    @Bean
    public TaxingClient taxingClient(RestProperties props, ObjectMapper mapper) {
        return Feign.builder()
                .decoder(new JacksonDecoder(mapper))
                .target(TaxingClient.class, props.getTaxingUri().toString());
    }
}
