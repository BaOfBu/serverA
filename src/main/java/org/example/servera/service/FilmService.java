package org.example.servera.service;

import org.example.servera.entity.Film;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;

import java.util.List;

@Service
public class FilmService {

    private final WebClient webClient;

    public FilmService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8081/api/filmsB/") // URL của Server B
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer ->
                                configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) // Tăng lên 10MB
                        )
                        .build())
                .build();
    }

    public List<Film> getFilmsFromServerB() {
        // Gọi API của Server B và lấy danh sách phim
        return webClient.get()
                .retrieve()
                .bodyToFlux(Film.class)
                .collectList()
                .block();
    }
}

