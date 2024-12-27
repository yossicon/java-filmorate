package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Slf4j
@Component
public class FilmStorage implements Storage<Film> {
    private final Map<Long, Film> films = new HashMap<>();
    private final Map<Long, Set<Long>> likes = new HashMap<>();

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }

    @Override
    public Film findById(Long filmId) {
        return films.get(filmId);
    }

    public Collection<Film> getMostPopular(Long count) {
        return findAll().stream()
                .filter(film -> likes.containsKey(film.getId()))
                .sorted(Comparator.comparing(this::getNumberOfLikes).reversed())
                .limit(count)
                .toList();
    }

    private Integer getNumberOfLikes(Film film) {
        return likes.get(film.getId()).size();
    }

    @Override
    public Film add(Film film) {
        film.setId(getNextFilmId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        Film oldFilm = films.get(newFilm.getId());
        oldFilm.setName(newFilm.getName());
        if (newFilm.getDescription() != null && !newFilm.getDescription().isBlank()) {
            oldFilm.setDescription(newFilm.getDescription());
        }
        if (newFilm.getReleaseDate() != null) {
            oldFilm.setReleaseDate(newFilm.getReleaseDate());
        }
        if (newFilm.getDuration() != null) {
            oldFilm.setDuration(newFilm.getDuration());
        }
        return oldFilm;
    }

    public Film addLike(Long id, Long userId) {
        likes.putIfAbsent(id, new HashSet<>());
        Set<Long> likesSet = likes.get(id);
        if (likesSet.contains(userId)) {
            throw new DuplicatedDataException(String.format("Пользователь c id %d уже поставил лайк фильму с id %d",
                    userId, id));
        }
        likesSet.add(userId);
        return films.get(id);
    }

    public Film deleteLike(Long id, Long userId) {
        likes.putIfAbsent(id, new HashSet<>());
        likes.get(id).remove(userId);
        return films.get(id);
    }

    private long getNextFilmId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
