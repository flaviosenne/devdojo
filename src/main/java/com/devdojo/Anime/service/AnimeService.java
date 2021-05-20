package com.devdojo.Anime.service;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.exception.BadRequestException;
import com.devdojo.Anime.repository.AnimeRepository;
import com.devdojo.Anime.requests.AnimePostRequestBody;
import com.devdojo.Anime.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private static List<Anime> animes;

    static {
       animes = new ArrayList<>(List.of(new Anime(1L,"Naruto"), new Anime(2L,"CDZ")));
    }
//    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animes;
//        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name){
        return animes.stream()
                .filter(anime -> anime.getName().equals(name))
                .collect(Collectors.toList());
//        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequest(Long id){
//        return animeRepository.findById(id)
//        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Anime not found"));
    }

    public Anime save(AnimePostRequestBody animeDTO) {
        Anime anime = Anime.builder().name(animeDTO.getName()).build();

        anime.setId(ThreadLocalRandom.current().nextLong(3, 100000));
        animes.add(anime);
//        return animeRepository.save(anime);
        return anime;
    }

    public void delete(Long id) {
        animes.remove(findByIdOrThrowBadRequest(id));
    }

    public void replace(AnimePutRequestBody animeDTO) {
        findByIdOrThrowBadRequest(animeDTO.getId());
        Anime anime = Anime.builder()
                .id(animeDTO.getId())
                .name(animeDTO.getName())
                .build();
//        animeRepository.save(anime);
        delete(anime.getId());
        animes.add(anime);
    }
}
