package com.devdojo.Anime.service;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.exception.BadRequestException;
import com.devdojo.Anime.repository.AnimeRepository;
import com.devdojo.Anime.requests.AnimePostRequestBody;
import com.devdojo.Anime.requests.AnimePutRequestBody;
import com.devdojo.Anime.util.AnimeCreator;
import com.devdojo.Anime.util.AnimePostRequestBodyCreator;
import com.devdojo.Anime.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setup(){
        List<Anime> anime = Arrays.asList(AnimeCreator.createValidAnime());
        BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(anime);


        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(anime);

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());


        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }
    @Test
    @DisplayName("list returns list of animes when successful")
    void list_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> listAnimes = animeService.listAll();

        Assertions.assertThat(listAnimes)
                .isNotNull()
                .isNotEmpty()
                .hasSizeGreaterThanOrEqualTo(1);

        Assertions.assertThat(listAnimes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns anime when send id is matcher in DB")
    void findById_ThrowsBadRequestException_WhenAnimeIsNotFound(){
        Long id = AnimeCreator.createValidAnime().getId();
        Optional<Anime> anime = Optional.ofNullable(animeService.findByIdOrThrowBadRequest(id));

        Assertions.assertThat(anime)
                .isNotNull();

        Assertions.assertThat(anime.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("findById throws BadRequestException when anime is not found")
    void findById_ReturnsAnime_WhenSendIdIsMatcherInDB(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());


        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThrowBadRequest(3543463136316L));
    }

    @Test
    @DisplayName("findByName returns list of animes when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeService.findByName(name);

        Assertions.assertThat(anime)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("findByName returns an empty list of animes when anime is not found")
    void findByName_ReturnsListOfAnime_WhenNotFound(){
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.EMPTY_LIST);

        List<Anime> anime = animeService.findByName("não existe");

        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        Anime anime = animeService.save(
                AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() ->
                animeService.replace(
                        AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){

        Assertions.assertThatCode(() ->
                animeService.delete(1L))
                .doesNotThrowAnyException();

    }
}