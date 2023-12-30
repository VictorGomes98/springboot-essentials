package com.devdojo.springboot.controller;

import com.devdojo.springboot.domain.Anime;
import com.devdojo.springboot.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2
@RequiredArgsConstructor // Generates a constructor for all final arguments
public class AnimeController {
    private final DateUtil dateUtil;

    @GetMapping(path = "list")
    // http://localhost:8080/anime/list
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        return List.of(new Anime("Boku no Hero"), new Anime("Berserk"));
    }
}
