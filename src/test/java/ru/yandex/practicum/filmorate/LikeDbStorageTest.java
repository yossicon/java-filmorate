package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({LikeDbStorage.class})
public class LikeDbStorageTest {
    private final LikeDbStorage likeDbStorage;
    private final NamedParameterJdbcOperations jdbc;

    @Test
    public void testAddLike() {
        likeDbStorage.addLike(1L, 1L);
        String query = """
                SELECT COUNT(*) FROM FILMS_LIKES
                WHERE FILM_ID = :film_id AND USER_ID = :user_id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("film_id", 1)
                .addValue("user_id", 1);
        Integer count = jdbc.queryForObject(query, parameters, Integer.class);

        assertThat(count)
                .isEqualTo(1);

    }

    @Test
    public void testDeleteLike() {
        likeDbStorage.addLike(1L, 1L);
        String query = """
                SELECT COUNT(*) FROM FILMS_LIKES
                WHERE FILM_ID = :film_id AND USER_ID = :user_id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("film_id", 1)
                .addValue("user_id", 1);
        Integer count = jdbc.queryForObject(query, parameters, Integer.class);

        assertThat(count)
                .isEqualTo(1);

        likeDbStorage.deleteLike(1L, 1L);
        String deleteQuery = """
                SELECT COUNT(*) FROM FILMS_LIKES
                WHERE FILM_ID = :film_id AND USER_ID = :user_id
                """;
        SqlParameterSource deleteParameters = new MapSqlParameterSource()
                .addValue("film_id", 1)
                .addValue("user_id", 1);
        Integer deleteCount = jdbc.queryForObject(deleteQuery, deleteParameters, Integer.class);

        assertThat(deleteCount)
                .isEqualTo(0);
    }
}
