package com.devdojo.springboot.repository;

import com.devdojo.springboot.domain.Animes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Animes, Long> {
    List<Animes> findByName(String name);
}
