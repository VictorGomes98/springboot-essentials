package com.devdojo.springboot.controller;

import com.devdojo.springboot.domain.Anime;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.dto.AnimePutRequestBody;
import com.devdojo.springboot.service.AnimeService;
import com.devdojo.springboot.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor // Generates a constructor for all final arguments
public class AnimeController {
    private final DateUtil dateUtil;
    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<Anime>> list() {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findByID(@PathVariable long id) {
        return ResponseEntity.ok(AnimeService.findByIDOrThrowBadRequestException(id));
    }
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody AnimePostRequestBody anime){
        return new ResponseEntity<>(AnimeService.save(anime), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        AnimeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        AnimeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
// http://localhost:8080/animes/list