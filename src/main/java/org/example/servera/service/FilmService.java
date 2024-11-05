package org.example.servera.service;

import org.example.servera.entity.Film;
import org.example.servera.security.SecretKeyServerBUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class FilmService {

    private final WebClient.Builder webClientBuilder;

    public FilmService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public List<Film> getFilmsFromServerB() throws NoSuchAlgorithmException {
        // get time, url to hash to create H
        String url = "/api/filmsB/";
        long time = new Date().getTime();
        // generate token
        String token = SecretKeyServerBUtils.generateToken(url, time);
        // create webclient

        WebClient webClient = webClientBuilder
                .baseUrl("http://localhost:8081/api/filmsB/") // URL của Server B
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Time", String.valueOf(time))
                .defaultHeader("Authorization", token)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024)) // Tăng lên 10MB
                        .build())
                .build();
        // Gọi API của Server B và lấy danh sách phim
        return webClient.get()
                .retrieve()
                .bodyToFlux(Film.class)
                .collectList()
                .block();
    }
}

