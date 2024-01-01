package com.devdojo.springboot.repository;

import com.devdojo.springboot.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
