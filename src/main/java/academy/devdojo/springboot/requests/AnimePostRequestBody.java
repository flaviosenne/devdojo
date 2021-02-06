package academy.devdojo.springboot.requests;


import javax.validation.constraints.NotEmpty;


import lombok.Data;


@Data
public class AnimePostRequestBody {
    @NotEmpty(message = "The name cannot be empty")
    private String name;
}
