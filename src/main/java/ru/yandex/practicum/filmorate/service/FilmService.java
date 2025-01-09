package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmService {
    Collection<Film> findAll();

    Film findById(Long filmId);

    Collection<Film> getMostPopular(Long count);

    Film add(Film film);

    Film update(Film film);

    Film addLike(Long id, Long userId);

    Film deleteLike(Long id, Long userId);
}
