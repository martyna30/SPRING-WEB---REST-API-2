package com.crud.tasks.service;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import org.junit.Assert;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void testFetchTrelloBoards() {
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1","test", trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoard = new ArrayList<>();
        mappedTrelloBoard.add(new TrelloBoard("1","test", mappedTrelloLists));


        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDto);

        List<TrelloBoardDto> fetchedTrelloBoards = trelloService.fetchTrelloBoards();

        Assert.assertEquals("1", fetchedTrelloBoards.get(0).getId());
        Assert.assertEquals("test", fetchedTrelloBoards.get(0).getName());
        Assert.assertEquals(trelloList, fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void createdTrelloCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test_id",
                "Test task",
                "Test Description",
                "top"
        );
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(//na tą klase mapuje odpowiedz z serwera
                "1",
                "Test task",
                "http://test.com"
        );

        Mail mail = new Mail("test@test.com", "Test", "Test message", "");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn(anyString());

        emailService.send(mail);

        //When
        CreatedTrelloCardDto newCard = trelloService.createdTrelloCardDto(trelloCardDto);//obiekt, który będzie przechowywał dane niezbędne do dodania zadania w Trello.TrelloCardDto:

        //Then
        verify(emailService, times(1)).send(mail);

        //Then
        Assert.assertNotNull(newCard);
        Assert.assertEquals("1", newCard.getId());
        Assert.assertEquals("Test task", newCard.getName());
    }
}




