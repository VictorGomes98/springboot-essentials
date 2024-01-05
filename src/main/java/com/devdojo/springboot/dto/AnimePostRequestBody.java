package com.devdojo.springboot.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime name can't be empty or null")
    private String name;
}
