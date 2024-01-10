package com.devdojo.springboot.util;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.dto.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody createAnimeToBeSaved() {
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createValidAnime().getName())
                .build();
    }
}
