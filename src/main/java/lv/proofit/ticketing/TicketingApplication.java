package lv.proofit.ticketing;

import lv.proofit.ticketing.rest.RestProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RestProperties.class)
public class TicketingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingApplication.class, args);
    }
}
