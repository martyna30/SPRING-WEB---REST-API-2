package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeWithMapperTest {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchBoards() {
        //Given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "my_task", trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoard = new ArrayList<>();
        mappedTrelloBoard.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        //when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDto);
        when(trelloMapper.mapToBoard(trelloBoardDto)).thenReturn(mappedTrelloBoard);
        when(trelloMapper.mapToBoardDto(anyList())).thenReturn(trelloBoardDto);


        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoard(trelloBoardDto);

        Assert.assertTrue(trelloBoardDto.get(0) instanceof TrelloBoardDto);

    }
    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test_id", "Test task", "Test Description", "top");

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test task", "http://test.com");////na tą klase mapuje odpowiedz z serwera

        TrelloCard trelloCard = new TrelloCard("Test task", "Test Description", "top", "test_id");

        //when(trelloFacade.createCart(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(trelloMapper.mapToCart(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCartDto(trelloCard)).thenReturn(trelloCardDto);
        //When
        //CreatedTrelloCardDto facadeCart = trelloFacade.createCart(trelloCardDto);//obiekt, który będzie przechowywał dane niezbędne do dodania zadania w Trello.TrelloCardDto:

        TrelloCard mappedTrelloCard = trelloMapper.mapToCart(trelloCardDto);

        TrelloCardDto trelloCardDtos = trelloMapper.mapToCartDto(trelloCard);
        //Then

        Assert.assertEquals("test_id", mappedTrelloCard.getListId());
        Assert.assertTrue(mappedTrelloCard instanceof TrelloCard);

        Assert.assertEquals("test_id", trelloCardDtos.getListId());
        Assert.assertTrue(trelloCardDtos instanceof TrelloCardDto);

        //Assert.assertTrue(facadeCart instanceof CreatedTrelloCardDto);
    }
}
