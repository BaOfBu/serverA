package org.example.servera.controller;

import org.example.servera.service.FilmService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/filmsA")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/")
    public List<String> getFilmsA() {
        // Lấy danh sách phim từ Server B qua FilmService
        return filmService.getFilmsFromServerB();
    }
}

