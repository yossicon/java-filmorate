package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Component
@RequiredArgsConstructor
public class FilmDbStorage implements Storage<Film> {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Film> findAll() {
        String filmQuery = """
                SELECT f.*,
                       m.RATING_ID,
                       m.RATING_NAME,
                FROM FILMS f
                JOIN MPA_RATING m ON f.RATING_ID = m.RATING_ID
                """;
        Map<Long, Film> films = new HashMap<>();
        jdbc.query(filmQuery, new FilmRowMapper())
                .forEach(film -> films.put(film.getId(), film));

        String genreQuery = """
                SELECT fg.*,
                       g.GENRE_NAME
                FROM FILMS_GENRES fg
                JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID
                """;
        jdbc.query(genreQuery, (resultSet) -> {
            Long filmId = resultSet.getLong("FILM_ID");
            Film film = films.get(filmId);

            if (film != null) {
                if (film.getGenres() == null) {
                    film.setGenres(new HashSet<>());
                }
                film.getGenres().add(new Genre(
                        resultSet.getInt("GENRE_ID"),
                        resultSet.getString("GENRE_NAME")
                ));
            }

        });
        return films.values().stream().toList();
    }

    @Override
    public Optional<Film> findById(Long id) {
        String filmQuery = """
                SELECT f.*,
                       m.RATING_ID,
                       m.RATING_NAME,
                FROM FILMS f
                JOIN MPA_RATING m ON f.RATING_ID = m.RATING_ID
                WHERE FILM_ID = :id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        List<Film> films = jdbc.query(filmQuery, parameters, new FilmRowMapper());
        if (films.isEmpty()) {
            return Optional.empty();
        }

        Film film = films.get(0);
        String genreQuery = """
                SELECT fg.GENRE_ID,
                       g.GENRE_NAME
                FROM FILMS_GENRES fg
                JOIN GENRES g ON fg.GENRE_ID = g.GENRE_ID
                WHERE FILM_ID = :id
                ORDER BY GENRE_ID
                """;
        Set<Genre> genres = new LinkedHashSet<>();
        jdbc.query(genreQuery, parameters, (resultSet) -> {
            genres.add(new Genre(
                    resultSet.getInt("GENRE_ID"),
                    resultSet.getString("GENRE_NAME")
            ));
        });
        film.setGenres(genres);
        return Optional.of(film);
    }

    @Override
    public Film add(Film film) {
        String filmQuery = """
                INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID)
                VALUES (:name, :description, :releaseDate, :duration, :mpaRating)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpaRating", film.getMpa().getId());
        jdbc.update(filmQuery, parameters, keyHolder, new String[]{"FILM_ID"});
        Number key = keyHolder.getKey();
        if (key != null) {
            Long filmId = key.longValue();
            film.setId(filmId);
        } else {
            throw new IllegalStateException("Id фильма не может быть null");
        }

        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            addGenresToFilm(film);
        } else {
            film.setGenres(new LinkedHashSet<>());
        }
        return film;
    }

    @Override
    public Film update(Film newFilm) {
        String filmQuery = """
                UPDATE FILMS SET NAME = :name,
                             DESCRIPTION = :description,
                             RELEASE_DATE = :releaseDate,
                             DURATION = :duration,
                             RATING_ID = :mpaRating
                WHERE FILM_ID = :filmId
                """;
        SqlParameterSource filmParameters = new MapSqlParameterSource()
                .addValue("filmId", newFilm.getId())
                .addValue("name", newFilm.getName())
                .addValue("description", newFilm.getDescription())
                .addValue("releaseDate", newFilm.getReleaseDate())
                .addValue("duration", newFilm.getDuration())
                .addValue("mpaRating", newFilm.getMpa().getId());
        jdbc.update(filmQuery, filmParameters);

        if (newFilm.getGenres() != null && !newFilm.getGenres().isEmpty()) {
            String deleteQuery = """
                    DELETE FROM FILMS_GENRES
                    WHERE FILM_ID = :filmId
                    """;
            Long filmId = newFilm.getId();
            SqlParameterSource deleteParameters = new MapSqlParameterSource("filmId", filmId);
            jdbc.update(deleteQuery, deleteParameters);
            addGenresToFilm(newFilm);
        } else {
            newFilm.setGenres(new LinkedHashSet<>());
        }
        return newFilm;
    }

    public List<Film> getMostPopular(Long count) {
        String query = """
                SELECT f.FILM_ID
                FROM FILMS f
                JOIN FILMS_LIKES fl ON f.FILM_ID = fl.FILM_ID
                GROUP BY f.FILM_ID
                ORDER BY COUNT(fl.FILM_ID) DESC
                LIMIT :count
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("count", count);
        List<Long> ids = jdbc.queryForList(query, parameters, Long.class);
        return ids.stream()
                .map(this::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private void addGenresToFilm(Film film) {
        String query = """
                INSERT INTO FILMS_GENRES (FILM_ID, GENRE_ID)
                VALUES (:filmId, :genreId)
                """;
        for (Genre genre : film.getGenres()) {
            SqlParameterSource namedGenreParameters = new MapSqlParameterSource()
                    .addValue("filmId", film.getId())
                    .addValue("genreId", genre.getId());
            jdbc.update(query, namedGenreParameters);
        }
    }
}
