package com.example.rickandmortyapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/characters")
@RequiredArgsConstructor
public class RickAndMortyCharacterController {

    private final RickAndMortyCharacterService rickAndMortyCharacterService;

    @GetMapping
    public List<RickAndMortyCharacter> getAllCharacters(@RequestParam @Nullable String status) {
        if (status != null)
            return rickAndMortyCharacterService.getCharactersByStatus(status);
        return rickAndMortyCharacterService.getCharacters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RickAndMortyCharacter> getCharacter(@PathVariable String id) {
        return ResponseEntity.of(rickAndMortyCharacterService.getCharacterById(id));
    }

    @GetMapping("species-statistic")
    int getStatisticForSpecies(@RequestParam String status, @RequestParam String species) {
        return rickAndMortyCharacterService.getStatisticForSpecies(status, species);
    }


}
