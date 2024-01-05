package com.devdojo.springboot.service;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.dto.AnimePutRequestBody;
import com.devdojo.springboot.exception.BadRequestException;
import com.devdojo.springboot.mapper.AnimesMapper;
import com.devdojo.springboot.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Animes> listAll() {
        return animeRepository.findAll();
    }
    public List<Animes> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Animes findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    public Animes save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimesMapper.INSTACE.toAnimes(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Animes savedAnimes = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
        Animes animes = AnimesMapper.INSTACE.toAnimes(animePutRequestBody);
        animes.setId(savedAnimes.getId());
        animeRepository.save(animes);
    }
}
