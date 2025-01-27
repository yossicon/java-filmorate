package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeDbStorage {
    private final NamedParameterJdbcOperations jdbc;

    public void addLike(Long filmId, Long userId) {
        String query = """
                INSERT INTO FILMS_LIKES (FILM_ID, USER_ID)
                VALUES (:filmId, :userId)
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("filmId", filmId)
                .addValue("userId", userId);
        jdbc.update(query, parameters);
    }

    public void deleteLike(Long filmId, Long userId) {
        String query = """
                DELETE FROM FILMS_LIKES
                WHERE FILM_ID = :filmId AND USER_ID = :userId
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("filmId", filmId)
                .addValue("userId", userId);
        jdbc.update(query, parameters);
    }
}
