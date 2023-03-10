package com.springboottest.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboottest.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.springboottest.mock.MockDataHolder.getDefaultPerson;
import static com.springboottest.mock.MockDataHolder.getDefaultPersons;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService personService;

    @Test
    public void shouldReturnAllPersons() throws Exception {
        when(personService.findAll()).thenReturn(getDefaultPersons());

        this.mockMvc.perform(get("/person"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void shouldFindPersonById() throws Exception {
        when(personService.findById(any())).thenReturn(Optional.of(getDefaultPerson()));

        this.mockMvc.perform(get("/person/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(getDefaultPerson().getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(getDefaultPerson().getLastName())));
    }

    @Test
    public void shouldAddPerson() throws Exception {
        when(personService.create(any())).thenReturn(getDefaultPerson());

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
        when(personService.update(anyLong(),any())).thenReturn(getDefaultPerson());

        this.mockMvc.perform(put("/person/{id}","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getDefaultPerson())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(getDefaultPerson().getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(getDefaultPerson().getLastName())));
    }

    @Test
    public void shouldDeletePerson() throws Exception {
        doNothing().when(personService).delete(anyLong());

        this.mockMvc.perform(delete("/person/{id}","1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(personService, times(1)).delete(anyLong());
    }
}