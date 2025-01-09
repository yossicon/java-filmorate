package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

@RestController
@RequestMapping("/films/{id}/like/{userId}")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final FilmService filmService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Добавление лайка к фильму с id {} от пользователя с id {}", id, userId);
        Film likedFilm = filmService.addLike(id, userId);
        log.info("Фильм {} успешно оценён", likedFilm.getName());
        return likedFilm;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public Film deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Удаление лайка к фильму с id {} от пользователя с id {}", id, userId);
        Film unlikedFilm = filmService.deleteLike(id, userId);
        log.info("Лайк фильма {} успешно удалён", unlikedFilm.getName());
        return unlikedFilm;
    }
}
