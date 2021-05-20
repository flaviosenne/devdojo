package com.devdojo.Anime.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@Entity
public class Anime implements Serializable{
    private static final long serialVersionUID = 1L;

    //    @Id @GeneratedValue(strategy = GeneratedType.IDENTITY)
    @JsonProperty(value = "id")
    private Long id;
    @JsonProperty(value = "name")
    private String name;

}
