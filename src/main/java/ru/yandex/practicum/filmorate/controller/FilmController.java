package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAll() {
        log.info("Получение всех фильмов");
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film findById(@PathVariable Long id) {
        log.info("Получение фильма по id");
        return filmService.findById(id);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> getMostPopular(@RequestParam(defaultValue = "10") Long count) {
        log.info("Получение рейтинга фильмов по лайкам");
        return filmService.getMostPopular(count);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film add(@Valid @RequestBody Film film) {
        log.info("Добавление фильма {}", film.getName());
        Film addedFilm = filmService.add(film);
        log.info("Фильм {} успешно добавлен с id {}", addedFilm.getName(), addedFilm.getId());
        return addedFilm;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film update(@Valid @RequestBody Film newFilm) {
        log.info("Обновление данных фильма {}", newFilm.getName());
        Film updatedFilm = filmService.update(newFilm);
        log.info("Данные фильма {} с id {} успешно обновлены", updatedFilm.getName(), updatedFilm.getId());
        return updatedFilm;
    }
}
