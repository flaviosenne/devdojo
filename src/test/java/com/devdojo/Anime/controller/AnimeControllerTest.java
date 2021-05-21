package com.devdojo.Anime.controller;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.requests.AnimePostRequestBody;
import com.devdojo.Anime.requests.AnimePutRequestBody;
import com.devdojo.Anime.service.AnimeService;
import com.devdojo.Anime.util.AnimeCreator;
import com.devdojo.Anime.util.AnimePostRequestBodyCreator;
import com.devdojo.Anime.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;


@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    void setup(){
        List<Anime> anime = Arrays.asList(AnimeCreator.createValidAnime());
        BDDMockito.when(animeServiceMock.listAll()).thenReturn(anime);


        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequest(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(anime);

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }
    @Test
    @DisplayName("list returns list of animes when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> listAnimes = animeController.list().getBody();

        Assertions.assertThat(listAnimes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(listAnimes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when send id is matcher in DB")
    void findById_ReturnsAnime_WhenSendIdIsMatcherInDB(){
        Long id = AnimeCreator.createValidAnime().getId();
        Optional<Anime> anime = Optional.ofNullable(animeController.findById(id).getBody());

        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("findByName returns list of animes when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeController.findByName(name).getBody();

        Assertions.assertThat(anime)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("findByName returns an empty list of animes when anime is not found")
    void findByName_ReturnsListOfAnime_WhenNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.EMPTY_LIST);

        List<Anime> anime = animeController.findByName("não existe").getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        Anime anime = animeController.save(
                AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() ->
        animeController.update(
                AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> update = animeController.update(
                AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(update).isNotNull();

        Assertions.assertThat(update.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() ->
                animeController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> update = animeController.delete(1L);

        Assertions.assertThat(update).isNotNull();

        Assertions.assertThat(update.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}