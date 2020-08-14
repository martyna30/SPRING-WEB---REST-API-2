package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloBoardDto {//obiekty DTO, które opuszczają naszą aplikację
    // tzn. są zwracane przez controller,
    // bądź są wysyłane do zewnętrznych serwisów.
    //Rest Template odpowiada za  przetwarzanie odpowiedzi serwera na obiekt w javie
    //tutaj podajemy tylko takie pola ktore chcemy otrzymać w odpowiedzi serwera

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lists")
    private List<TrelloListDto> lists;





}
