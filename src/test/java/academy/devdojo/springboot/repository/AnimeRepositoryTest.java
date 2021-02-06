package academy.devdojo.springboot.repository;

import academy.devdojo.springboot.domain.Anime;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@DisplayName("Test for AnimeRepository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Should persist an Anime created in DB")
    void save_PersistAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        Assertions.assertThat(animeSaved).isNotNull();
        
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Should release an Anime created in DB")
    void save_Update_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        animeSaved.setName("Batman");

        Anime animeUpdated = this.animeRepository.save(animeSaved);


        Assertions.assertThat(animeUpdated).isNotNull();
        
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

    }

    @Test
    @DisplayName("Should delete an Anime created in DB")
    void delete_RemoveAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        animeSaved.setName("Batman");

       this.animeRepository.delete(animeSaved);

       Optional<Anime> animeOptional =this.animeRepository.findById(animeSaved.getId());

       Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Should Returns List of Anime with method find by name")
    void findByName_ReturnsListAnime_WhenSuccessful(){
        Anime animeToBeSaved = createAnime();
        
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        
        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

       Assertions.assertThat(animes).isNotEmpty().contains(animeSaved);

        Assertions.assertThat(animes).contains(animeSaved);
    }

    @Test
    @DisplayName("Should Returns List empty with no anime found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound(){
      
        List<Anime> animes = this.animeRepository.findByName("name invalid");

       Assertions.assertThat(animes).isEmpty();
       
    }

    @Test
    @DisplayName("Should return exception when save Anime the name is not provided")
    void save_Exception_WhenIsNotProvided(){

        Anime anime = new Anime();

        // Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
        // .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> this.animeRepository.save(anime))
        .withMessageContaining("The name cannot be empty");
        
    }
    private Anime createAnime(){
    return Anime.builder()
            .name("One Piece")
            .build();
    }
   
}