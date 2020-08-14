package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)//udostępnienie controllera do testowania,Dzięki adnotacji @WebMvcTest możemy również korzystać z MockMvc
public class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;//MockMvc - jest to klasa pozwalająca na wykonywanie żądań HTTP do naszego controllera

    @MockBean//Używamy jej gdy korzystamy z @RunWith(SpringRunner.class). Pozwala nam na dodanie zachowań klasie
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);

        //When & Then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))//(get)deklaruje metodzie perform(), jakiego rodzaju żądanie ma zostać wysłane
                .andExpect(status().is(200))//isOk() tes może być// sprawdzania wyniku żądania
                .andExpect(jsonPath("$", hasSize(0)));//sprawdzenie czy lista jest pusta, $oznacza odpowiedz, $.key odwołanie do konkretnego klucza

    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {

        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "Test List", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1","Test Task", trelloList));

       when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDto);

       mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               //Trello board fields
               .andExpect(jsonPath("$", hasSize(1)))//oczekuje tablicy o rozmiarze 1
               .andExpect(jsonPath("$[0].id", is("1")))
               .andExpect(jsonPath("$[0].name", is("Test Task")))
               //Trello list fields
               .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id",is("1")))
               .andExpect(jsonPath("$[0].lists[0].name",is("Test List")))
               .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }


    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("1", "Test", "Test Description", "top");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("323", "Test", "http://test.com");////na tą klase mapuje odpowiedz z serwera

        when(trelloFacade.createCart(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);//potrzebane mapowanie na json

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto); //mapowanie na json (dzięki bibliotece gson)

        //When & Then
        mockMvc.perform(post("/v1/trello/createTrelloCard")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id",is("323"))) //odpowiedź zwracana przez controller nie jest listą a zwykłem JSONem, w naszych testach musimy się odwołać do konkretnych pól zamiast do indeksów
                .andExpect(jsonPath("$.name",is("Test")))
                .andExpect(jsonPath("$.shortUrl",is("http://test.com")));
    }

}