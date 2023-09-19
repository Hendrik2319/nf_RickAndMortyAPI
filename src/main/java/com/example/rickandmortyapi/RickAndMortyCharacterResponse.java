package com.example.rickandmortyapi;

import java.util.List;

public record RickAndMortyCharacterResponse(RepsonseInfo info, List<RickAndMortyCharacter> results) {

    public record RepsonseInfo(int count, int pages, String next, String prev) {}
}
