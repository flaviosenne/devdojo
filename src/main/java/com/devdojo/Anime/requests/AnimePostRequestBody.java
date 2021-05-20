package com.devdojo.Anime.requests;

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
    private String name;

}
