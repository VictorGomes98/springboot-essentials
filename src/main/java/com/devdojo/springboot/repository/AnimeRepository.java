package com.devdojo.springboot.repository;

import com.devdojo.springboot.domain.Animes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Animes, Long> {

}
