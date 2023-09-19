package com.example.rickandmortyapi;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class RickAndMortyCharacterService {

    private final WebClient webClient = WebClient.create("https://rickandmortyapi.com/api/character");

    public List<RickAndMortyCharacter> getCharacters() {

        ResponseEntity<RickAndMortyCharacterListResponse> responseEntity = webClient
                .get()
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.empty())
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.empty())
                .toEntity(RickAndMortyCharacterListResponse.class)
                .block();

        if (responseEntity==null) return List.of();

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode.is4xxClientError()) return List.of();
        if (statusCode.is5xxServerError()) return List.of();

        RickAndMortyCharacterListResponse response = responseEntity.getBody();
        if (response==null) return List.of();

        return response.results();
    }

    public Optional<RickAndMortyCharacter> getCharacter(String id) {

        ResponseEntity<RickAndMortyCharacterResponse> responseEntity = webClient
                .get()
                .uri("/"+id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.empty())
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.empty())
                .toEntity(RickAndMortyCharacterResponse.class)
                .block();

        if (responseEntity==null) return Optional.empty();

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode.is4xxClientError()) return Optional.empty();
        if (statusCode.is5xxServerError()) return Optional.empty();

        RickAndMortyCharacterResponse response = responseEntity.getBody();
        if (response==null) return Optional.empty();

        return response.toCharacter();
    }
}
