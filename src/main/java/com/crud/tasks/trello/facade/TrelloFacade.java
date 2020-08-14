package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TrelloFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloFacade.class);

    @Autowired
    private TrelloService trelloService;

    @Autowired
    private TrelloMapper trelloMapper;

    @Autowired
    TrelloValidator trelloValidator;

    public List<TrelloBoardDto> fetchTrelloBoards() {
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoard(trelloService.fetchTrelloBoards());//return List<TrelloBoardDto> tu obiekt jest pobrany przez RestTemplate i zmapowany na obiekt DTO
        //jest mapowana na TrelloBoard i poddana filtracji, a potem mapowana na List<TrelloBoardDto>
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);
        return trelloMapper.mapToBoardDto(filteredBoards);//Na koniec już przefiltrowana i zmapowana tablica trafia do kontrolera gdzie jest wysyłane żadanie
    }

    public CreatedTrelloCardDto createCart(final TrelloCardDto trelloCardDto) {
        TrelloCard trelloCard = trelloMapper.mapToCart(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        return trelloService.createdTrelloCardDto(trelloMapper.mapToCartDto(trelloCard));
    }
}