package com.devdojo.Anime.integration;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.repository.AnimeRepository;
import com.devdojo.Anime.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort // get port in execution
    private int port;
    
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("list returns list of animes when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName = savedAnime.getName();

        List<Anime> listAnimes = testRestTemplate.exchange("/animes",
                HttpMethod.GET,null,
                new ParameterizedTypeReference<List<Anime>>(){}).getBody();


        Assertions.assertThat(listAnimes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(listAnimes.get(0).getName()).isEqualTo(expectedName);
    }

}
