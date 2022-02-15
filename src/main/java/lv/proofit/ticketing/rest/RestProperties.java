package lv.proofit.ticketing.rest;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.net.URI;

@Value
@Validated
@ConstructorBinding
@ConfigurationProperties("rest")
public class RestProperties {

    @NotNull URI taxingUri;
    @NotNull URI pricingUri;

}
