package academy.devdojo.springboot.controller;

import academy.devdojo.springboot.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/anime")
public class AnimeController {
    @GetMapping
    public List<Anime> list(){
        return List.of(new Anime("Naruto"), new Anime("One Peace"));
    }
}
