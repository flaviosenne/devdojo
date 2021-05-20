package com.devdojo.Anime.repository;

import com.devdojo.Anime.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Save created anime when successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime animeSaved = animeRepository.save(anime);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(anime.getName());
    }

    private Anime createAnime(){
        return Anime.builder().name("Anime para teste").build();
    }
}