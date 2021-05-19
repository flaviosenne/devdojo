package com.devdojo.Anime.service;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

//    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return List.of(new Anime(1L,"Naruto"), new Anime(2L,"CDZ"));
    }
}
