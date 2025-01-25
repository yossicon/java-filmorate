package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final FilmService filmService;
    private final UserService userService;
    private final LikeDbStorage likeDbStorage;

    @Override
    public void addLike(Long id, Long userId) {
        Film film = filmService.findById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        likeDbStorage.addLike(id, userId);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        Film film = filmService.findById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        likeDbStorage.deleteLike(id, userId);
    }
}
