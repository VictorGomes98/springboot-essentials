package com.devdojo.springboot.util;

import com.devdojo.springboot.domain.Animes;

public class AnimeCreator {
    public static Animes createAnimeToBeSaved() {
        return Animes.builder()
                .name("Shingeki no kyojin")
                .build();
    }
    public static Animes createValidAnime() {
        return Animes.builder()
                .name("Shingeki no kyojin")
                .id(1L)
                .build();
    }
    public static Animes createAnimeToBeUpdated() {
        return Animes.builder()
                .name("Attack on titan")
                .id(1L)
                .build();
    }
}
