package com.devdojo.springboot.service;

import com.devdojo.springboot.domain.Anime;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.dto.AnimePutRequestBody;
import com.devdojo.springboot.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private static AnimeRepository animeRepository;

    public List<Anime> listAll(){
        return animeRepository.findAll();
    }
    public static Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
    }

    public static void delete(long id) {
        animeRepository.delete(findByIDOrThrowBadRequestException(id));
    }

    public static void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIDOrThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = Anime.builder()
                .id(savedAnime.getId())
                .name(animePutRequestBody.getName())
                .build();
    }

    public static Anime findByIDOrThrowBadRequestException(long id) {
        return animeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
    }
}
