package org.example.servera.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FilmService {

    private final WebClient webClient;

    public FilmService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8081/api/filmsB") // URL của Server B
                .build();
    }

    public List<String> getFilmsFromServerB() {
        // Gọi API của Server B và lấy danh sách phim
        return webClient.get()
                .retrieve()
                .bodyToFlux(String.class)
                .collectList()
                .block();
    }
}

