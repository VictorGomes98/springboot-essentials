package com.devdojo.springboot.controller;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.service.AnimeService;
import com.devdojo.springboot.util.AnimeCreator;
import com.devdojo.springboot.util.AnimePostRequestBodyCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;
    @Mock
    AnimeService animeServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Animes> animesPage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any())).thenReturn(animesPage);

        BDDMockito.when(animeServiceMock.listAllNonPageable()).thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("List: returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Animes> animesPage = animeController.list(null).getBody();

        Assertions.assertThat(animesPage).isNotNull();
        Assertions.assertThat(animesPage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animesPage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll: returns list with all animes inside page object when successful")
    void listAll_ReturnsListWithAllAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Animes> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findById: returns an anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Animes anime = animeController.findByID(expectedId).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of animes when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Animes> animes = animeController.findByName(expectedName).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName: returns an empty list of animes when anime is not found")
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        String expectedName = "Not an anime";
        List<Animes> animes = animeController.findByName(expectedName).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }    @Test
    @DisplayName("save: returns an anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful(){
        Animes anime = animeController.save(AnimePostRequestBodyCreator.createAnimeToBeSaved()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
}