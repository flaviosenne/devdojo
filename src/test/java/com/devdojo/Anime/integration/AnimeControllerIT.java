package com.devdojo.Anime.integration;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.repository.AnimeRepository;
import com.devdojo.Anime.requests.AnimePostRequestBody;
import com.devdojo.Anime.util.AnimeCreator;
import com.devdojo.Anime.util.AnimePostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @Test
    @DisplayName("findById returns anime when send id is matcher in DB")
    void findById_ReturnsAnime_WhenSendIdIsMatcherInDB(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long id = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, id);

        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("findByName returns list of animes when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String name = savedAnime.getName();

        String url = String.format("/animes/find?name=%s",name);

        List<Anime> anime = testRestTemplate.exchange(url,
                HttpMethod.GET,null,
                new ParameterizedTypeReference<List<Anime>>(){}).getBody();

        Assertions.assertThat(anime)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("findByName returns an empty list of animes when anime is not found")
    void findByName_ReturnsListOfAnime_WhenNotFound(){
        List<Anime> anime = testRestTemplate.exchange("/animes/find?name=não tem",
                HttpMethod.GET,null,
                new ParameterizedTypeReference<List<Anime>>(){}).getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        ResponseEntity<Anime> anime = testRestTemplate.postForEntity("/animes",animePostRequestBody, Anime.class);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(anime.getBody()).isNotNull();
        Assertions.assertThat(anime.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        savedAnime.setName("new name");

        ResponseEntity<Void> anime = testRestTemplate.exchange("/animes",
                HttpMethod.PUT,
                new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> anime = testRestTemplate.exchange("/animes/{id}",
                HttpMethod.DELETE,
                null, Void.class, savedAnime.getId());

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
