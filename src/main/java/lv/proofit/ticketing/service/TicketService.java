package lv.proofit.ticketing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.proofit.ticketing.enums.AgeGroup;
import lv.proofit.ticketing.model.DraftRequest;
import lv.proofit.ticketing.model.DraftTicket;
import lv.proofit.ticketing.model.Item;
import lv.proofit.ticketing.model.Passenger;
import lv.proofit.ticketing.rest.pricing.PricingClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketService {

    private final PricingClient pricingClient;
    private final TaxService taxService;

    public DraftTicket generateDraft(DraftRequest request) {
        log.info("Generating a draft ticket for request containing: {}", request);
        List<Item> items = request.getPassengers().stream()
                .flatMap(this::calculatePrice)
                .collect(toList());

        BigDecimal total = items.stream()
                .map(Item::getCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DraftTicket(total, items);
    }

    private Stream<Item> calculatePrice(Passenger passenger) {
        BigDecimal basePrice = pricingClient.getBasePrice(passenger.getDestinationTerminalName()).getPrice();
        basePrice = taxService.applyTax(basePrice);
        return Stream.of(
                        ticketItem(basePrice, passenger.getAgeGroup()),
                        luggageItem(basePrice, passenger.getLuggageAmount()))
                .filter(Objects::nonNull);
    }

    private Item ticketItem(BigDecimal basePrice, AgeGroup ageGroup) {
        BigDecimal cost = ageGroup.applyDiscount(basePrice);
        return new Item(String.format("Ticket for %s", ageGroup.name().toLowerCase()), cost);
    }

    private Item luggageItem(BigDecimal basePrice, int luggageCount) {
        if (luggageCount < 1) {
            return null;
        }
        BigDecimal bagsMultiplier = BigDecimal.valueOf(luggageCount).multiply(BigDecimal.valueOf(0.3d));
        BigDecimal cost = basePrice.multiply(bagsMultiplier);
        return new Item(String.format("%d bag(s)", luggageCount), cost);
    }
}
