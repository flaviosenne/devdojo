package com.devdojo.Anime.client;

import com.devdojo.Anime.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args){
//        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:81/animes", Anime.class, 2);
//        log.info(entity);

        Anime[] animes = new RestTemplate().getForObject("http://localhost:81/animes", Anime[].class);
        log.info(Arrays.toString(animes));

        ResponseEntity<List<Anime>> listAnime = new RestTemplate().exchange("http://localhost:81/animes",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
                }
        );
        log.info(listAnime.getBody());
    }
}
