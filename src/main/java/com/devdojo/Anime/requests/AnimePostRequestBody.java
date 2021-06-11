package com.devdojo.Anime.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AnimePostRequestBody {
    @NotEmpty(message = "name isn't empty or null")
    @Schema(description = "Thisi is the Animes's name", example = "Naruto", required = true)
    private String name;

}
