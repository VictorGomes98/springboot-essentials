package com.devdojo.springboot.service;

import com.devdojo.springboot.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {
    private static ArrayList<Anime> animes = new ArrayList<>(
            List.of(new Anime(1L, "Boku no Hero"), new Anime(2L, "Berserk"))
    );

    public static Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 1000000));
        animes.add(anime);
        return anime;
    }

    public static void delete(long id) {
        animes.remove(findByID(id));
    }

    public static void replace(Anime anime) {
        delete(anime.getId());
        animes.add(anime);
    }

    public List<Anime> listAll() {
        return animes;
    }

    public static Anime findByID(long id) {
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }
}
