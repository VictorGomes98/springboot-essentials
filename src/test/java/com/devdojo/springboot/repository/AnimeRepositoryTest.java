package com.devdojo.springboot.repository;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.util.AnimeCreator;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@Log4j2
@DataJpaTest
@DisplayName("Tests for animeRepository")
class AnimeRepositoryTest {
    @Inject
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save: persists an anime when successful")
    void save_PersistAnime_WhenSuccessful() {
        Animes animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Animes animeSaved = this.animeRepository.save(animeToBeSaved);
        log.info(animeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }
    @Test
    @DisplayName("Save: throws ConstraintViolationException when name is empty")
    void save_throwsConstraintViolationException_NameIsEmpty() {
        Animes animeToBeSaved = new Animes();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(animeToBeSaved))
//                .isInstanceOf(ConstraintViolationException.class);
//
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(animeToBeSaved))
                .withMessageContaining("The anime name can't be empty or null");

    }

    @Test
    @DisplayName("Save: update an anime when successful")
    void save_UpdateAnime_WhenSuccessful() {
        Animes animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Animes animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("Attack on titan");
        Animes animeUpdated = this.animeRepository.save(animeSaved);
        log.info(animeUpdated);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

    }

    @Test
    @DisplayName("Delete: delete an anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Animes animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Animes animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);
        Optional<Animes> animeDeleted = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeDeleted).isEmpty();
    }

    @Test
    @DisplayName("FindByName: returns a list of anime when successful")
    void findByName_returnsListOfAnime_WhenSuccessful() {
        Animes animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Animes animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();
        List<Animes> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotEmpty().contains(animeSaved);
    }

    @Test
    @DisplayName("FindByName: returns an empty list of anime when successful")
    void findByName_returnsEmptyListOfAnime_WhenSuccessful() {
        Animes animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Animes animeSaved = this.animeRepository.save(animeToBeSaved);

        List<Animes> animes = this.animeRepository.findByName("not an anime");

        Assertions.assertThat(animes).isEmpty();
    }


}