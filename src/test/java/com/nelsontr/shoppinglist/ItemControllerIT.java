package com.nelsontr.shoppinglist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelsontr.shoppinglist.dto.ItemRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/v1/items returns at least the initial dataset")
    void listItems_returnsInitialData() throws Exception {
        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(10))))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[0].name", not(blankOrNullString())));
    }

    @Test
    @DisplayName("Happy path CRUD: create -> get -> update -> delete")
    void crudFlow_works() throws Exception {
        // Create
        ItemRequestDto create = new ItemRequestDto();
        create.setName("Test Apples");
        create.setPrice(new BigDecimal("1.20"));
        create.setQuantity(5);
        create.setCategory("Produce");

        ResultActions createRes = mockMvc.perform(post("/api/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(create)));

        createRes.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.cost", is(6.00)))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.updatedAt", notNullValue()));

        String locationId = createRes.andReturn().getResponse().getContentAsString();
        Long id = objectMapper.readTree(locationId).get("id").asLong();

        // Get
        mockMvc.perform(get("/api/v1/items/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.name", is("Test Apples")));

        // Update
        ItemRequestDto update = new ItemRequestDto();
        update.setName("Updated Apples");
        update.setPrice(new BigDecimal("1.50"));
        update.setQuantity(4);
        update.setCategory("Produce");

        mockMvc.perform(put("/api/v1/items/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Apples")))
                .andExpect(jsonPath("$.cost", is(6.00)));

        // Delete
        mockMvc.perform(delete("/api/v1/items/" + id))
                .andExpect(status().isNoContent());

        // Verify not found
        mockMvc.perform(get("/api/v1/items/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Item not found with id")));
    }

    @Test
    @DisplayName("Validation errors return 400 with ApiError body")
    void create_withInvalidPayload_returnsBadRequest() throws Exception {
        // Missing name, negative price, zero quantity
        String payload = "{\n" +
                "  \"price\": -1,\n" +
                "  \"quantity\": 0,\n" +
                "  \"category\": \"X\"\n" +
                "}";

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Bad Request")))
                .andExpect(jsonPath("$.message", allOf(
                        containsString("name: must not be blank"),
                        containsString("price: must be greater than"),
                        containsString("quantity: must be greater than 0")
                )))
                .andExpect(jsonPath("$.path", is("/api/v1/items")));
    }

    @Test
    @DisplayName("GET by id returns 404 with ApiError when not found")
    void getById_notFound() throws Exception {
        mockMvc.perform(get("/api/v1/items/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", is("Item not found with id: 999999")))
                .andExpect(jsonPath("$.path", is("/api/v1/items/999999")));
    }
}
