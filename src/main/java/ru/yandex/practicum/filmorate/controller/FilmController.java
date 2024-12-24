package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Получение всех фильмов");
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("Получение фильма по id");
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public Collection<Film> getMostPopular(@RequestParam(defaultValue = "10") Long count) {
        log.info("Получение рейтинга фильмов по лайкам");
        return filmService.getMostPopular(count);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Добавление фильма {}", film.getName());
        Film addedFilm = filmService.addFilm(film);
        log.info("Фильм {} успешно добавлен с id {}", addedFilm.getName(), addedFilm.getId());
        return addedFilm;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film newFilm) {
        log.info("Обновление данных фильма {}", newFilm.getName());
        Film updatedFilm = filmService.updateFilm(newFilm);
        log.info("Данные фильма {} с id {} успешно обновлены", updatedFilm.getName(), updatedFilm.getId());
        return updatedFilm;
    }

    @PutMapping("{id}/like/{userId}")
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Добавление лайка к фильму с id {} от пользователя с id {}", id, userId);
        Film likedFilm = filmService.addLike(id, userId);
        log.info("Фильм {} успешно оценён", likedFilm.getName());
        return likedFilm;
    }

    @DeleteMapping("{id}/like/{userId}")
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Удаление лайка к фильму с id {} от пользователя с id {}", id, userId);
        Film unlikedFilm = filmService.deleteLike(id, userId);
        log.info("Лайк фильма {} успешно удалён", unlikedFilm.getName());
        return unlikedFilm;
    }
}
