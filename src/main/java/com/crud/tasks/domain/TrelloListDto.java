package com.crud.tasks.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)//Konsekwencją niezastosowania tej adnotacji jest zgłoszenie wyjątku przez Springa, mówiącego o tym,
// że odpowiedź z serwera jest inna niż ta, którą chcemy zmapować na obiekt.
public class TrelloListDto {//nie zwróci innych pól niż są zawarte w klasie

    @JsonProperty
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private boolean isClosed;
}
