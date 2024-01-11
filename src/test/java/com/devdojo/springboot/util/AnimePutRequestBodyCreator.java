package com.devdojo.springboot.util;

import com.devdojo.springboot.dto.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createValidUpdateAnime() {
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidAnime().getId())
                .name(AnimeCreator.createValidAnime().getName())
                .build();
    }
}
