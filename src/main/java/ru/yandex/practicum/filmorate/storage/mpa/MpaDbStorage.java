package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

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

    public Mpa findMpaById(Integer id) {
        String query = """
                SELECT * FROM MPA_RATING
                WHERE RATING_ID = :id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        return jdbc.queryForObject(query, parameters, new MpaRowMapper());
    }
}
