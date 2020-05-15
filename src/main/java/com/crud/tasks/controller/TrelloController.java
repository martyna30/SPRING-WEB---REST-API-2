package com.crud.tasks.controller;

import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*") //pozwoli na komunikację serwera warstwy frontendowej z warstwą backendową
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {
    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {

        return trelloClient.getTrelloBoards();

        /*trelloBoards.stream()
                .filter(trelloBoardDto -> trelloBoardDto.getName() != null)
                .filter(trelloBoardDto -> trelloBoardDto.getId() != null)
                .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                .forEach(trelloBoardDto -> {

                    System.out.println(trelloBoardDto.getName() + " " + trelloBoardDto.getId());

                    System.out.println("This board contains lists: ");

                    trelloBoardDto.getLists().forEach(trelloList ->
                            System.out.println(trelloList.getName() + " " + trelloList.getId() + " " + trelloList.isClosed()));
                });*/
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCard createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}

