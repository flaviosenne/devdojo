package com.devdojo.Anime.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//@Entity
public class Anime {
//    @Id @GeneratedValue(strategy = GeneratedType.IDENTITY)
    private Long id;
    private String name;

}
