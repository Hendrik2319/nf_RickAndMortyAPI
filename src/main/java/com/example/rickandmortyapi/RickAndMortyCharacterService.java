package com.example.rickandmortyapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class RickAndMortyCharacterService {

    private final WebClient webClient;

    public RickAndMortyCharacterService(@Value("${com.example.rickandmortyapi.url}") String url) {
        this.webClient = WebClient.create(url);
    }

    private <ResponseType> ResponseType getResponse(String uri, Class<ResponseType> clazz) {
        ResponseEntity<ResponseType> responseEntity = webClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.empty())
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.empty())
                .toEntity(clazz)
                .block();

        if (responseEntity==null) return null;

        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if (statusCode.is4xxClientError()) return null;
        if (statusCode.is5xxServerError()) return null;

        return responseEntity.getBody();
    }

    private List<RickAndMortyCharacter> getCharacterListResponse(String uri) {
        RickAndMortyCharacterListResponse response = getResponse(uri, RickAndMortyCharacterListResponse.class);
        if (response==null) return List.of();

        return response.results();
    }

    private Optional<RickAndMortyCharacter> getCharacterResponse(String uri) {
        RickAndMortyCharacterResponse response = getResponse(uri, RickAndMortyCharacterResponse.class);
        if (response==null) return Optional.empty();

        return response.toCharacter();
    }

    public List<RickAndMortyCharacter> getCharacters() {
        return getCharacterListResponse("");
    }

    public Optional<RickAndMortyCharacter> getCharacterById(String id) {
        return getCharacterResponse("/" + id);
    }

    public List<RickAndMortyCharacter> getCharactersByStatus(String status) {
        return getCharacterListResponse("?status=" + status);
    }

    public int getStatisticForSpecies(String request, String species) {
        String newUri = "?status=" + request + "&species=" + species;
        RickAndMortyCharacterListResponse response = getResponse(newUri, RickAndMortyCharacterListResponse.class);
        if (response==null) return 0;
        if (response.info()==null) return 0;
        return response.info().count();
    }
}
