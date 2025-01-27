package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
@Slf4j
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Genre> findAllGenres() {
        log.info("Получение всех жанров");
        return genreService.findAllGenres();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre findGenreById(@PathVariable Integer id) {
        log.info("Получение жанра по id {}", id);
        return genreService.findGenreById(id);
    }
}

