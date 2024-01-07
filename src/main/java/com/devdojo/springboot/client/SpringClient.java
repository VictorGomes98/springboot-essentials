package com.devdojo.springboot.client;

import com.devdojo.springboot.domain.Animes;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Animes> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/1", Animes.class);
        log.info(entity+"\n");

        Animes entity1 = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Animes.class, 2);
        log.info(entity1+"\n"); // Returns the object without the information that we have using getForEntity

        Animes[] entity2 = new RestTemplate().getForObject("http://localhost:8080/animes/all", Animes[].class);
        log.info(Arrays.toString(entity2)+"\n"); // Returns an Array of objects

        ResponseEntity<List<Animes>> animesList = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        log.info(animesList.getBody()+"\n"); // Returns a list of objects

//        Animes anime = Animes.builder().name("Kingdom").build();
//        Animes kingdom = new RestTemplate().postForObject("http://localhost:8080/animes", anime, Animes.class);
//        log.info(kingdom+"\n");

        Animes anime = Animes.builder().name("Sakurasou pet no kanojo").build();
        ResponseEntity<Animes> animeSaved = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST, new HttpEntity<>(anime, createJsonHeader()), Animes.class);

        log.info(animeSaved+"\n");

    }
    private static HttpHeaders createJsonHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
