package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(Long id);

    List<Film> getMostPopular(Long count);

    Film add(Film film);

    Film update(Film film);
}
