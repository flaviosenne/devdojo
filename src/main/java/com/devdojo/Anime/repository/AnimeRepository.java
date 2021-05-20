package com.devdojo.Anime.repository;

import com.devdojo.Anime.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> findByName(String name);
}
