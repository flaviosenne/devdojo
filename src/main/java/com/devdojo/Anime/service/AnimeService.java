package com.devdojo.Anime.service;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private List<Anime> animes = List.of(new Anime(1L,"Naruto"), new Anime(2L,"CDZ"));
//    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animes;
    }

    public Anime findById(Long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }
}
