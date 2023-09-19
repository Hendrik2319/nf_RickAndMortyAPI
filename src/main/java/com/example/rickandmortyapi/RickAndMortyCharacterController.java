package com.example.rickandmortyapi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/characters")
@RequiredArgsConstructor
public class RickAndMortyCharacterController {

    private final RickAndMortyCharacterService rickAndMortyCharacterService;

    @GetMapping
    public List<RickAndMortyCharacter> getAllCharacters() {
        return rickAndMortyCharacterService.getCharacters();
    }


}
