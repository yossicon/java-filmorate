package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDbStorage genreDbStorage;

    @Override
    public List<Genre> findAllGenres() {
        return genreDbStorage.findAllGenres();
    }

    @Override
    public Genre findGenreById(Integer id) {
        Genre genre = genreDbStorage.findGenreById(id);
        if (genre == null) {
            throw new NotFoundException(String.format("Жанр с id %d не найден", id));
        }
        return genre;
    }
}
