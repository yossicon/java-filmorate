package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ParameterNotValidException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmDbStorage filmDbStorage;

    @Override
    public List<Film> findAll() {
        return filmDbStorage.findAll();
    }

    @Override
    public Film findById(Long id) {
        Film film = filmDbStorage.findById(id);
        if (film == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", id));
        }
        return film;
    }

    @Override
    public List<Film> getMostPopular(Long count) {
        if (count <= 0) {
            throw new ParameterNotValidException("size", "Размер должен быть больше нуля");
        }
        return filmDbStorage.getMostPopular(count);
    }

    @Override
    public Film add(Film film) {
        return filmDbStorage.add(film);
    }

    @Override
    public Film update(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id не указан");
        }
        Film oldFilm = filmDbStorage.findById(newFilm.getId());
        if (oldFilm == null) {
            throw new NotFoundException(String.format("Фильм с id %d не найден", newFilm.getId()));
        }
        return filmDbStorage.update(newFilm);
    }
}