package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GenreDbStorage {
    private final NamedParameterJdbcOperations jdbc;

    public List<Genre> findAllGenres() {
        String query = "SELECT * FROM GENRES";
        return jdbc.query(query, new GenreRowMapper()).stream()
                .sorted(Comparator.comparing(Genre::getId))
                .toList();
    }

    public Optional<Genre> findGenreById(Integer id) {
        String query = """
                SELECT * FROM GENRES
                WHERE GENRE_ID = :id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        List<Genre> genres = jdbc.query(query, parameters, new GenreRowMapper());
        return genres.isEmpty() ? Optional.empty() : Optional.of(genres.get(0));
    }
}
