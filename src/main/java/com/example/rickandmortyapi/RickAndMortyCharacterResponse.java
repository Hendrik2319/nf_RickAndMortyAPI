package com.example.rickandmortyapi;

import org.springframework.lang.Nullable;

import java.util.Optional;

public record RickAndMortyCharacterResponse(@Nullable int id, @Nullable String name, @Nullable String species, @Nullable String error) {

    @SuppressWarnings("unused")
    public boolean isError() {
        return error!=null;
    }

    public Optional<RickAndMortyCharacter> toCharacter() {
        if (error==null)
            return Optional.of(new RickAndMortyCharacter(id, name, species));
        return Optional.empty();
    }
}
