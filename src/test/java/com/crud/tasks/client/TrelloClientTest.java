package com.crud.tasks.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)//Mock to sztuczny obiekt, któremu możemy nadawać specyficzne zachowania w trakcie testowania.
public class TrelloClientTest {

    @InjectMocks // adnotacja, która tworzy realny obiekt. Pozostałe pola klasy testującej oznaczone adnotacją
    // @Mock zostaną w nią wstrzyknięte.
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "test_id",
                "Test task",
                "Test Description",
                "top"
        );
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard(//na tą klase mapuje odpowiedz z serwera
                "1",
                "Test task",
                "http://test.com"
        );
        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);
        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);//obiekt, który będzie przechowywał dane niezbędne do dodania zadania w Trello.TrelloCardDto:
        //Then
        Assert.assertEquals("1", newCard.getId());
        Assert.assertEquals("Test task", newCard.getName());
        Assert.assertEquals("http://test.com",newCard.getShortUrl());
    }

    @Test
    public void shouldFetchTrelloBoards () throws URISyntaxException {
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloToken()).thenReturn("test");

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/username/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();
        //Then
        Assert.assertEquals(1, fetchedTrelloBoards.size());
        Assert.assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        Assert.assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        Assert.assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        TrelloBoardDto[] trelloTestBoard = null;

        URI uri = new URI("http://test.com/members/username/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(trelloTestBoard);

        //When
        List<TrelloBoardDto> testBoard = trelloClient.getTrelloBoards();

        //Then
        Assert.assertEquals(0, testBoard.size());

    }

}
