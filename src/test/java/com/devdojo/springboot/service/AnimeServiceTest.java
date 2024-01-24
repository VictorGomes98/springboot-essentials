package com.devdojo.springboot.service;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.exception.BadRequestException;
import com.devdojo.springboot.repository.AnimeRepository;
import com.devdojo.springboot.util.AnimeCreator;
import com.devdojo.springboot.util.AnimePostRequestBodyCreator;
import com.devdojo.springboot.util.AnimePutRequestBodyCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Animes> animesPage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animesPage);

        BDDMockito.when(animeRepositoryMock.findAll()).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Animes.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Animes.class));

    }

    @Test
    @DisplayName("ListAll: returns list with all animes inside page object when successful")
    void listAll_ReturnsListWithAllAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Animes> animesPage = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(animesPage).isNotNull();
        Assertions.assertThat(animesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animesPage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAllNonPageable: returns list with all animes when successful")
    void listAllNonPageable_ReturnsListWithAllAnimes_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Animes> animes = animeService.listAllNonPageable();

        Assertions.assertThat(animes)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException: returns an anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Animes anime = animeService.findByIdOrThrowBadRequestException(expectedId);

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }
    @Test
    @DisplayName("findByIdOrThrowBadRequestException: throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_throwsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> this.animeService.findByIdOrThrowBadRequestException(1));

    }

    @Test
    @DisplayName("findByName: returns an anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Animes> animes = animeService.findByName(expectedName);

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list of animes when anime is not found")
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        String expectedName = "Not an anime";
        List<Animes> animes = animeService.findByName(expectedName);

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save: returns an anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Animes anime = animeService.save(AnimePostRequestBodyCreator.createAnimeToBeSaved());

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace: updates anime when successful")
    void replace_updatesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createValidUpdateAnime()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete: removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();

    }
}