package com.springboottest.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.springboottest.mock.MockDataHolder.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PersonRestAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnAllPersons() throws Exception {
        this.mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(NUM_OF_PERSONS)));
    }

    @Test
    public void shouldFindPersonById() throws Exception {
        this.mockMvc.perform(get("/person/{id}", String.valueOf(PERSON_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Vasia")))
                .andExpect(jsonPath("$.lastName", equalTo("Pupkin")));
    }

    @Test
    public void shouldAddPerson() throws Exception {
        this.mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDefaultPerson())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(getDefaultPerson().getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(getDefaultPerson().getLastName())));
    }

    @Test
    public void shouldUpdatePerson() throws Exception {
        this.mockMvc.perform(put("/person/{id}", String.valueOf(PERSON_ID))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDefaultPerson())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(getDefaultPerson().getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(getDefaultPerson().getLastName())));

        this.mockMvc.perform(get("/person/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(getDefaultPerson().getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(getDefaultPerson().getLastName())));
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        this.mockMvc.perform(delete("/person/{id}", "1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(NUM_OF_PERSONS - 1)));
    }
}
