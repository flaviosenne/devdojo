package com.devdojo.Anime.controller;

import com.devdojo.Anime.domain.Anime;
import com.devdojo.Anime.requests.AnimePostRequestBody;
import com.devdojo.Anime.requests.AnimePutRequestBody;
import com.devdojo.Anime.service.AnimeService;
import com.devdojo.Anime.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Anime>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll());
    }

    @GetMapping("/all")
    @Operation(summary = "List all animes", tags = {"anime"})
    public ResponseEntity<List<Anime>> listAll(@AuthenticationPrincipal UserDetails userDetails){
        log.info(userDetails);
        return ResponseEntity.ok(animeService.listAll());
    }

    @CrossOrigin
    @GetMapping(value = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @CrossOrigin
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id){
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequest(id));
    }


    @CrossOrigin
    @PostMapping(path = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime){
        return ResponseEntity.status(201).body(animeService.save(anime));
    }

    @CrossOrigin
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Success when delete successful operation"),
            @ApiResponse(responseCode = "400", description = "When Not found anime in DB"),
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        animeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequestBody anime){
        animeService.replace(anime);
        return ResponseEntity.noContent().build();
    }
}
