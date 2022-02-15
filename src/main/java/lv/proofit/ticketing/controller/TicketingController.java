package lv.proofit.ticketing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lv.proofit.ticketing.model.DraftRequest;
import lv.proofit.ticketing.model.DraftTicket;
import lv.proofit.ticketing.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Validator;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Tag(name = "Ticketing", description = "TicketingController")
@RequestMapping(path = "/ticketing", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class TicketingController {

    private final TicketService ticketService;
    private final Validator validator;

    @PostMapping("/draft")
    public DraftTicket draftTicket(@Validated @RequestBody DraftRequest request) {
        int violationCount = request.getPassengers().stream()
                .map(passenger -> validator.validate(passenger))
                .map(Set::size)
                .reduce(Integer::sum)
                .orElse(0);

        if (violationCount > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided data did not pass validation");
        }

        return ticketService.generateDraft(request);
    }
}
