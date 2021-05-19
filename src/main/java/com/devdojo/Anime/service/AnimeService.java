package com.devdojo.Anime.service;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
    }

    public Anime findById(Long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 100000));
        animes.add(anime);
        return anime;
    }

    public void delete(Long id) {
        animes.remove(findById(id));
    }
}
