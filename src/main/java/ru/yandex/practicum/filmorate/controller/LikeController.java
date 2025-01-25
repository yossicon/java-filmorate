package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.LikeService;

@RestController
@RequestMapping("/films/{id}/like/{userId}")
@RequiredArgsConstructor
@Slf4j
public class LikeController {
    private final LikeService likeService;

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Добавление лайка к фильму с id {} от пользователя с id {}", id, userId);
        likeService.addLike(id, userId);
        log.info("Фильм успешно оценён");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Удаление лайка к фильму с id {} от пользователя с id {}", id, userId);
        likeService.deleteLike(id, userId);
        log.info("Лайк успешно удалён");
    }
}
