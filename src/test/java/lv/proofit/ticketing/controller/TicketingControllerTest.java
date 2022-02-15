package lv.proofit.ticketing.controller;

import lv.proofit.ticketing.enums.AgeGroup;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static lv.proofit.ticketing.constant.TestConstants.TERMINAL;
import static lv.proofit.ticketing.enums.AgeGroup.ADULT;
import static lv.proofit.ticketing.enums.AgeGroup.CHILD;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void acceptanceCriteria() throws Exception {
        performRequest(draftRequest(
                passenger(ADULT, TERMINAL, 2),
                passenger(CHILD, TERMINAL, 1)
        )).andExpectAll(
                jsonPath("$.total", equalTo(29.04d)),
                jsonPath("$.items[0].description", equalTo("Ticket for adult")),
                jsonPath("$.items[0].cost", equalTo(12.10d)),
                jsonPath("$.items[1].description", equalTo("2 bag(s)")),
                jsonPath("$.items[1].cost", equalTo(7.26d)),
                jsonPath("$.items[2].description", equalTo("Ticket for child")),
                jsonPath("$.items[2].cost", equalTo(6.05d)),
                jsonPath("$.items[3].description", equalTo("1 bag(s)")),
                jsonPath("$.items[3].cost", equalTo(3.63d)));
    }

    @Test
    void emptyRequest() throws Exception {
        performRequest("").andExpect(status().isBadRequest());
    }

    @Test
    void emptyObject() throws Exception {
        performRequest(new JSONObject()).andExpect(status().isBadRequest());
    }

    @Test
    void noAgeGroup() throws Exception {
        performRequest(draftRequest(passenger(null, TERMINAL, 0)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void blankTerminalName() throws Exception {
        performRequest(draftRequest(passenger(ADULT, "", 0)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidLuggage() throws Exception {
        performRequest(draftRequest(passenger(ADULT, TERMINAL, -1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void noPassengers() throws Exception {
        performRequest(draftRequest()).andExpectAll(
                jsonPath("$.total", equalTo(0.0d)),
                jsonPath("$.items", empty()));
    }

    private ResultActions performRequest(JSONObject requestContent) throws Exception {
        return performRequest(requestContent.toString());
    }

    private ResultActions performRequest(String requestContent) throws Exception {
        return mvc.perform(post("/ticketing/draft")
                .contentType(APPLICATION_JSON)
                .content(requestContent));
    }

    // ============= DTO Factories ============

    private JSONObject draftRequest(JSONObject... passengers) {
        return new JSONObject()
                .put("passengers", new JSONArray(passengers));
    }

    private JSONObject passenger(AgeGroup ageGroup, String destinationTerminalName, int luggageAmount) {
        return new JSONObject()
                .put("ageGroup", ageGroup)
                .put("destinationTerminalName", destinationTerminalName)
                .put("luggageAmount", luggageAmount);
    }
}