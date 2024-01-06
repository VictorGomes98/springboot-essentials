package com.devdojo.springboot.controller;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.dto.AnimePutRequestBody;
import com.devdojo.springboot.service.AnimeService;
import com.devdojo.springboot.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<Animes>> list(Pageable pageable) {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll(pageable));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Animes> findByID(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    @GetMapping(path = "/find/{name}")
    public ResponseEntity<List<Animes>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }
    @PostMapping
    public ResponseEntity<Animes> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
        log.info(animePostRequestBody);
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
// http://localhost:8080/animes/list