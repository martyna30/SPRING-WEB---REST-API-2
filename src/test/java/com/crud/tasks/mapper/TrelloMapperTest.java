package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {
    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoard() {
        //Given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "my_Board", trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoard = new ArrayList<>();
        mappedTrelloBoard.add(new TrelloBoard("1", "my Board", mappedTrelloLists));

        when(trelloMapper.mapToBoard(trelloBoardDto)).thenReturn(mappedTrelloBoard);

        //When
        List<TrelloBoard> mappedtrelloBoards = trelloMapper.mapToBoard(trelloBoardDto);

        //Then
        Assert.assertNotNull(mappedtrelloBoards);
        Assert.assertEquals(1, mappedtrelloBoards.size());
        Assert.assertEquals("my Board", mappedtrelloBoards.get(0).getName());

        Assert.assertEquals("my_Board", trelloBoardDto.get(0).getName());
    }
    @Test
    public void testMapToBoardDto() {
        //Given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "my_Board", trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoard = new ArrayList<>();
        mappedTrelloBoard.add(new TrelloBoard("1", "my_Board", mappedTrelloLists));

        when(trelloMapper.mapToBoardDto(mappedTrelloBoard)).thenReturn(trelloBoardDto);

        //When
        List<TrelloBoardDto> trelloBoardsDtos = trelloMapper.mapToBoardDto(mappedTrelloBoard);

        //Then
        Assert.assertNotNull(trelloBoardsDtos);
        Assert.assertEquals(1, trelloBoardsDtos.size());
        Assert.assertEquals("my_Board", trelloBoardsDtos.get(0).getName());

        Assert.assertEquals("my_Board", mappedTrelloBoard.get(0).getName());
    }
    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "my_Board", trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoard = new ArrayList<>();
        mappedTrelloBoard.add(new TrelloBoard("1", "my_Board", mappedTrelloLists));

        when(trelloMapper.mapToList(trelloList)).thenReturn(mappedTrelloLists);

        //When
        List<TrelloList> mappedTrellolist = trelloMapper.mapToList(trelloList);

        //Then
        Assert.assertNotNull(mappedTrellolist);
        Assert.assertEquals(1, mappedTrellolist.size());
        Assert.assertEquals(false, mappedTrellolist.get(0).isClosed());

        Assert.assertEquals(false, trelloList.get(0).isClosed());
    }
    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloListDto> trelloList = new ArrayList<>();
        trelloList.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "my_Board", trelloList));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoard = new ArrayList<>();
        mappedTrelloBoard.add(new TrelloBoard("1", "my_Board", mappedTrelloLists));

        when(trelloMapper.mapToListDto(mappedTrelloLists)).thenReturn(trelloList);

        //When
        List<TrelloListDto> listDtos = trelloMapper.mapToListDto(mappedTrelloLists);

        //Then
        Assert.assertNotNull(listDtos);
        Assert.assertEquals(1, listDtos.size());
        Assert.assertEquals(false, listDtos.get(0).isClosed());

        Assert.assertEquals(false, mappedTrelloLists.get(0).isClosed());
    }
    @Test
    public void testMapToCartDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test","test cart","top","1");

        TrelloCardDto trelloCardDto = new TrelloCardDto("1","test","test cart","top");

        when(trelloMapper.mapToCartDto(trelloCard)).thenReturn(trelloCardDto);

        //When
        TrelloCardDto theCartDto = trelloMapper.mapToCartDto(trelloCard);

        //Then
        Assert.assertNotNull(theCartDto);
        Assert.assertEquals("1", theCartDto.getListId());
        Assert.assertEquals("test cart", theCartDto.getDescription());

        Assert.assertEquals("1", trelloCard.getListId());
        Assert.assertEquals("test cart", trelloCard.getDescription());
    }
    @Test
    public void testMapToCart() {
        //Given
        TrelloCard trelloCard = new TrelloCard("test","test cart","top","1");

        TrelloCardDto trelloCardDto = new TrelloCardDto("1","test","test cart","top");

        when(trelloMapper.mapToCart(trelloCardDto)).thenReturn(trelloCard);

        //When
        TrelloCard theCart = trelloMapper.mapToCart(trelloCardDto);

        //Then
        Assert.assertNotNull(theCart);
        Assert.assertEquals("1", theCart.getListId());
        Assert.assertEquals("test cart", theCart.getDescription());

        Assert.assertEquals("1", trelloCardDto.getListId());
        Assert.assertEquals("test cart", trelloCardDto.getDescription());
    }
}
