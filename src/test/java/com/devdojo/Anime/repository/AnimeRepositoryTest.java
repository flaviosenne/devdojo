package com.devdojo.Anime.repository;

import com.devdojo.Anime.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Save persists anime when successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime animeSaved = animeRepository.save(anime);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(anime.getName());
    }

    @Test
    @DisplayName("Save updated anime when successful")
    void save_UpdatedAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime animeSaved = animeRepository.save(anime);

        animeSaved.setName("outro nome");

        Anime animeUpdated =animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when successful")
    void delete_RemoveAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime animeSaved = animeRepository.save(anime);

        animeRepository.delete(animeSaved);

        Optional<Anime> animeDeleted = animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeDeleted).isEmpty();

    }

    @Test
    @DisplayName("Find By Name returns list of anime when successful")
    void findByName_ReturnListAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime animeSaved = animeRepository.save(anime);

        String name = animeSaved.getName();

        List<Anime> animes = animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotEmpty()
        .contains(animeSaved);

    }

    @Test
    @DisplayName("Find By Name returns empty list when no anime found")
    void findByName_ReturnEmptyList_WhenAnimesNotFound(){
         List<Anime> animes = animeRepository.findByName("xaxaxaxaxaax");

        Assertions.assertThat(animes).isEmpty();

    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = new Anime();

//        Assertions.assertThatThrownBy(() -> animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> animeRepository.save(anime))
                .withMessageContaining("name isn't empty or null");
    }


    private Anime createAnime(){
        return Anime.builder().name("Anime para teste").build();
    }
}