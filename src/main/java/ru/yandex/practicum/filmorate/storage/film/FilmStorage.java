package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> getFilms();

    Film getFilmById(Long filmId);

    Collection<Film> getMostPopular(Long count);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film addLike(Long id, Long userId);

    Film deleteLike(Long id, Long userId);
}
