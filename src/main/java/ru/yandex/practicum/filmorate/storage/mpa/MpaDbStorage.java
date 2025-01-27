package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MpaDbStorage {
    private final NamedParameterJdbcOperations jdbc;

    public List<Mpa> findAllMpa() {
        String query = """
                SELECT * FROM MPA_RATING
                ORDER BY RATING_ID
                """;
        return jdbc.query(query, new MpaRowMapper());
    }

    public Optional<Mpa> findMpaById(Integer id) {
        String query = """
                SELECT * FROM MPA_RATING
                WHERE RATING_ID = :id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        List<Mpa> ratings = jdbc.query(query, parameters, new MpaRowMapper());
        return ratings.isEmpty() ? Optional.empty() : Optional.of(ratings.get(0));
    }
}
