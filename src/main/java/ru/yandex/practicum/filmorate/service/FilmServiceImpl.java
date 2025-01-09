package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ParameterNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Override
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public Film findById(Long filmId) {
        Film film = filmStorage.findById(filmId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", filmId));
        }
        return film;
    }

    @Override
    public Collection<Film> getMostPopular(Long count) {
        if (count <= 0) {
            throw new ParameterNotValidException("size", "Размер должен быть больше нуля");
        }
        return filmStorage.getMostPopular(count);
    }

    @Override
    public Film add(Film film) {
        return filmStorage.add(film);
    }

    @Override
    public Film update(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id не указан");
        }
        Film oldFilm = filmStorage.findById(newFilm.getId());
        if (oldFilm == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", newFilm.getId()));
        }
        return filmStorage.update(newFilm);
    }

    @Override
    public Film addLike(Long id, Long userId) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        User user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        return filmStorage.addLike(id, userId);
    }

    @Override
    public Film deleteLike(Long id, Long userId) {
        Film film = filmStorage.findById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        User user = userStorage.findById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        return filmStorage.deleteLike(id, userId);
    }
}