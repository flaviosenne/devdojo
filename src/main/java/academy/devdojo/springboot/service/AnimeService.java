package academy.devdojo.springboot.service;

import academy.devdojo.springboot.domain.Anime;
import academy.devdojo.springboot.exception.BadRequestException;
import academy.devdojo.springboot.repository.AnimeRepository;
import academy.devdojo.springboot.requests.AnimePostRequestBody;
import academy.devdojo.springboot.requests.AnimePutRequestBody;
import academy.maper.AnimeMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class AnimeService {
   
    private final AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }
    public List<Anime> findByName(String name){
        return animeRepository.findByName(name);
    }
    public Anime findByIdOrThrowBadRequestException(Long id){
        return animeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Anime not Found"));
        
    }

    @Transactional(rollbackOn =  Exception.class)
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        Anime save = animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
        return save;
    }
    public void delete(Long id){
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }
    public Anime update(AnimePutRequestBody animePutRequestBody){
        Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        return animeRepository.save(anime);    
    }

}
