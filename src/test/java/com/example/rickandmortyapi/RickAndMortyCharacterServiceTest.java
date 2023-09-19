package com.example.rickandmortyapi;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RickAndMortyCharacterServiceTest {

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    private RickAndMortyCharacterService rickAndMortyCharacterService;

    @BeforeEach
    void setupEach() {
        rickAndMortyCharacterService = new RickAndMortyCharacterService(mockWebServer.url("/").toString());
    }

    @Test
    void whenGetCharacters_getsCalled() {
        // Given
        mockWebServer.enqueue(
                new MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setBody("""
                                {
                                    "info": {
                                        "count": 1,
                                        "pages": 1,
                                        "next": null,
                                        "prev": null
                                    },
                                    "results": [
                                        {
                                            "id": 204,
                                            "name": "Lisa",
                                            "status": "Dead",
                                            "species": "Alien",
                                            "type": "",
                                            "gender": "Female",
                                            "origin": {
                                                "name": "unknown",
                                                "url": ""
                                            },
                                            "location": {
                                                "name": "Immortality Field Resort",
                                                "url": "https://rickandmortyapi.com/api/location/7"
                                            },
                                            "image": "https://rickandmortyapi.com/api/character/avatar/204.jpeg",
                                            "episode": [
                                                "https://rickandmortyapi.com/api/episode/26"
                                            ],
                                            "url": "https://rickandmortyapi.com/api/character/204",
                                            "created": "2017-12-30T12:59:58.460Z"
                                        }
                                    ]
                                }
                                """)
        );

        // When
        List<RickAndMortyCharacter> actual = rickAndMortyCharacterService.getCharacters();

        // Then
        List<RickAndMortyCharacter> expected = List.of(new RickAndMortyCharacter(204, "Lisa", "Alien"));
        assertEquals(expected, actual);
    }

    @Test
    void whenGetCharactersByStatus_getsCalled() {
        // Given
        mockWebServer.enqueue(
                new MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setBody("""
                                {
                                    "info": {
                                        "count": 1,
                                        "pages": 1,
                                        "next": null,
                                        "prev": null
                                    },
                                    "results": [
                                        {
                                            "id": 204,
                                            "name": "Lisa",
                                            "status": "Dead",
                                            "species": "Alien",
                                            "type": "",
                                            "gender": "Female",
                                            "origin": {
                                                "name": "unknown",
                                                "url": ""
                                            },
                                            "location": {
                                                "name": "Immortality Field Resort",
                                                "url": "https://rickandmortyapi.com/api/location/7"
                                            },
                                            "image": "https://rickandmortyapi.com/api/character/avatar/204.jpeg",
                                            "episode": [
                                                "https://rickandmortyapi.com/api/episode/26"
                                            ],
                                            "url": "https://rickandmortyapi.com/api/character/204",
                                            "created": "2017-12-30T12:59:58.460Z"
                                        }
                                    ]
                                }
                                """)
        );

        // When
        List<RickAndMortyCharacter> actual = rickAndMortyCharacterService.getCharactersByStatus("XXXXXXXXX");

        // Then
        List<RickAndMortyCharacter> expected = List.of(new RickAndMortyCharacter(204, "Lisa", "Alien"));
        assertEquals(expected, actual);
    }

    @Test
    void whenGetCharacterById_getsValidId() {
        // Given
        mockWebServer.enqueue(
                new MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setBody("""
                                {
                                    "id": 204,
                                    "name": "Lisa",
                                    "status": "Dead",
                                    "species": "Alien",
                                    "type": "",
                                    "gender": "Female",
                                    "origin": {
                                        "name": "unknown",
                                        "url": ""
                                    },
                                    "location": {
                                        "name": "Immortality Field Resort",
                                        "url": "https://rickandmortyapi.com/api/location/7"
                                    },
                                    "image": "https://rickandmortyapi.com/api/character/avatar/204.jpeg",
                                    "episode": [
                                        "https://rickandmortyapi.com/api/episode/26"
                                    ],
                                    "url": "https://rickandmortyapi.com/api/character/204",
                                    "created": "2017-12-30T12:59:58.460Z"
                                }
                                """)
        );

        // When
        Optional<RickAndMortyCharacter> actual = rickAndMortyCharacterService.getCharacterById("XXXXXXXXX");

        // Then
        assertNotNull(actual);
        assertTrue(actual.isPresent());
        RickAndMortyCharacter expected = new RickAndMortyCharacter(204, "Lisa", "Alien");
        assertEquals(expected, actual.get());
    }

    @Test
    void whenGetCharacterById_getsInvalidId() {
        // Given
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
        );

        // When
        Optional<RickAndMortyCharacter> actual = rickAndMortyCharacterService.getCharacterById("XXXXXXXXX");

        // Then
        assertNotNull(actual);
        assertFalse(actual.isPresent());
    }

    @Test
    void whenGetStatisticForSpecies_getsCalled() {
        // Given
        mockWebServer.enqueue(
                new MockResponse()
                        .setHeader("Content-Type", "application/json")
                        .setBody("""
                                {
                                    "info": {
                                        "count": 1,
                                        "pages": 1,
                                        "next": null,
                                        "prev": null
                                    },
                                    "results": [
                                        {
                                            "id": 204,
                                            "name": "Lisa",
                                            "status": "Dead",
                                            "species": "Alien",
                                            "type": "",
                                            "gender": "Female",
                                            "origin": {
                                                "name": "unknown",
                                                "url": ""
                                            },
                                            "location": {
                                                "name": "Immortality Field Resort",
                                                "url": "https://rickandmortyapi.com/api/location/7"
                                            },
                                            "image": "https://rickandmortyapi.com/api/character/avatar/204.jpeg",
                                            "episode": [
                                                "https://rickandmortyapi.com/api/episode/26"
                                            ],
                                            "url": "https://rickandmortyapi.com/api/character/204",
                                            "created": "2017-12-30T12:59:58.460Z"
                                        }
                                    ]
                                }
                                """)
        );

        // When
        int actual = rickAndMortyCharacterService.getStatisticForSpecies("WWWWWW", "WWWWWW");

        // Then
        int expected = 1;
        assertEquals(expected, actual);
    }

}