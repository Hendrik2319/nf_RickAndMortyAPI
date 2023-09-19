package com.example.rickandmortyapi;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RickAndMortyApiIntegrationTests {

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @DynamicPropertySource
    static void setUrlDynamically(DynamicPropertyRegistry reg) {
        reg.add("com.example.rickandmortyapi.url", ()->mockWebServer.url("/").toString());
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAllCharacters_calledWithoutParameter() throws Exception {
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
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters")
                )

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 204,
                                "name": "Lisa",
                                "species": "Alien"
                            }
                        ]
                        """));
    }

    @Test
    void whenGetAllCharacters_calledWithStatusParameter() throws Exception {
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
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters?status=wwwwwwwwww")
                )

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "id": 204,
                                "name": "Lisa",
                                "species": "Alien"
                            }
                        ]
                        """));
    }

    @Test
    void whenGetCharacter_calledWithValidId() throws Exception {
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
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters/abcdef")
                )

                // Then
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 204,
                            "name": "Lisa",
                            "species": "Alien"
                        }
                        """));
    }

    @Test
    void whenGetCharacter_calledWithInvalidId() throws Exception {
        // Given
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
        );

        // When
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/characters/abcdef")
                )

                // Then
                .andExpect(status().is(404))
        ;
    }

}
