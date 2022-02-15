package lv.proofit.ticketing.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.Options;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * Creates mocks of base price and tax web-services.
 * This class would not be needed if those services existed.
 */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class MockServerConfiguration {

    private final RestProperties props;

    @Bean
    public WireMockServer taxingServer() {
        WireMockServer server = configureServerAndStart(props.getTaxingUri());
        configureFor(props.getTaxingUri().getPort());
        stubFor(get(urlEqualTo("/taxes")).willReturn(aResponse().withBody(
                new JSONObject().put("taxRates", new JSONArray().put(0.21)).toString())));
        return server;
    }

    @Bean
    public WireMockServer pricingServer() {
        WireMockServer server = configureServerAndStart(props.getPricingUri());
        configureFor(props.getPricingUri().getPort());
        stubFor(get(urlMatching("/pricing/.*"))
                .willReturn(aResponse().withBody(new JSONObject().put("price", 10).toString())));
        return server;
    }

    private WireMockServer configureServerAndStart(URI uri) {
        Options options = wireMockConfig()
                .bindAddress(uri.getPath())
                .port(uri.getPort());
        WireMockServer server = new WireMockServer(options);
        server.start();
        return server;
    }
}
