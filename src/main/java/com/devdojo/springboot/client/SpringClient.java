package com.devdojo.springboot.client;

import com.devdojo.springboot.domain.Animes;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Animes> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/1", Animes.class);
        log.info(entity);

        Animes entity1 = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Animes.class,22);
        log.info(entity1); // Returns the object without the information that we have using getForEntity
    }
}
