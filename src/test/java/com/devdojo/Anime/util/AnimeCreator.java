package com.devdojo.Anime.util;

import com.devdojo.Anime.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder().name("Anime para teste").build();
    }
    public static Anime createValidAnime(){
        return Anime.builder().name("Anime para teste").id(1L).build();
    }
    public static Anime createValidUpdatedAnime(){
        return Anime.builder().name("Anime para teste 2").id(1L).build();
    }
}
