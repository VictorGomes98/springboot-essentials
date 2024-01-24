package com.devdojo.springboot.integration;

import com.devdojo.springboot.domain.Animes;
import com.devdojo.springboot.dto.AnimePostRequestBody;
import com.devdojo.springboot.repository.AnimeRepository;
import com.devdojo.springboot.util.AnimeCreator;
import com.devdojo.springboot.util.AnimePostRequestBodyCreator;
import com.devdojo.springboot.wrapper.PageableResponse;
import jakarta.inject.Inject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIt {
    @Inject
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Inject
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("List: returns list of animes inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        Animes savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        PageableResponse<Animes> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET,
                null, new ParameterizedTypeReference<PageableResponse<Animes>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("ListAll: returns list with all animes inside page object when successful")
    void listAll_ReturnsListWithAllAnimesInsidePageObject_WhenSuccessful() {
        Animes savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Animes> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Animes>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById: returns an anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Animes savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedId = savedAnime.getId();
        Animes anime = testRestTemplate.getForObject("/animes/{id}", Animes.class, expectedId);

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of animes when successful")
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        Animes savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getName();

        List<Animes> animes = testRestTemplate.exchange("/animes/find/" + expectedName, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Animes>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes.getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list of animes when anime is not found")
    void findByName_ReturnsEmptyListOfAnimes_WhenAnimeIsNotFound() {
        List<Animes> animes = testRestTemplate.exchange("/animes/find/not an anime", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Animes>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save: returns an anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        AnimePostRequestBody animeToBeSaved = AnimePostRequestBodyCreator.createAnimeToBeSaved();

        ResponseEntity<Animes> animesResponseEntity = testRestTemplate.postForEntity(
                "/animes", animeToBeSaved, Animes.class);

        Assertions.assertThat(animesResponseEntity).isNotNull();
        Assertions.assertThat(animesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animesResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animesResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace: updates anime when successful")
    void replace_updatesAnime_WhenSuccessful() {
        Animes savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        savedAnime.setName("new name");

        ResponseEntity<Void> animesResponseEntity = testRestTemplate.exchange(
                "/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(animesResponseEntity).isNotNull();
        Assertions.assertThat(animesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete: removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Animes savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animesResponseEntity = testRestTemplate.exchange(
                "/animes/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(animesResponseEntity).isNotNull();
        Assertions.assertThat(animesResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
