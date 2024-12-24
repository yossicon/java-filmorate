package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilmById(Long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", filmId));
        }
        return film;
    }

    public Collection<Film> getMostPopular(Long count) {
        if (count <= 0) {
            throw new ParameterNotValidException("size", "Размер должен быть больше нуля");
        }
        return filmStorage.getMostPopular(count);
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id не указан");
        }
        Film oldFilm = filmStorage.getFilmById(newFilm.getId());
        if (oldFilm == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", newFilm.getId()));
        }
        return filmStorage.updateFilm(newFilm);
    }

    public Film addLike(Long id, Long userId) {
        Film film = filmStorage.getFilmById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        return filmStorage.addLike(id, userId);
    }

    public Film deleteLike(Long id, Long userId) {
        Film film = filmStorage.getFilmById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        User user = userStorage.getUserById(userId);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }
        return filmStorage.deleteLike(id, userId);
    }
}