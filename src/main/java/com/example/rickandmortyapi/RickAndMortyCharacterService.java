package com.example.rickandmortyapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class RickAndMortyCharacterService {

    private final WebClient webClient = WebClient.create("https://rickandmortyapi.com/api/character");

    public List<RickAndMortyCharacter> getCharacters() {

        ResponseEntity<RickAndMortyCharacterResponse> responseEntity = webClient
                .get()
                .uri("")
                .retrieve()
                .toEntity(RickAndMortyCharacterResponse.class)
                .block();

        if (responseEntity==null) return List.of();

        RickAndMortyCharacterResponse response = responseEntity.getBody();
        if (response==null) return List.of();

        return response.results();
    }
}
