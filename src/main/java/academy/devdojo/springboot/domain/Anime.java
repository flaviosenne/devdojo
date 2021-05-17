package academy.devdojo.springboot.domain;

import lombok.*;

@NoArgsConstructor
@Builder
@ToString
public class Anime {
    private String name;

    public Anime(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
