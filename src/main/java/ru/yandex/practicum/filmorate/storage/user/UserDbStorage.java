package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDbStorage implements Storage<User> {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<User> findAll() {
        String query = "SELECT * FROM USERS";
        return jdbc.query(query, new UserRowMapper());
    }

    @Override
    public Optional<User> findById(Long id) {
        String query = """
                SELECT * FROM USERS
                WHERE USER_ID = :id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("id", id);
        List<User> users = jdbc.query(query, parameters, new UserRowMapper());
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public User add(User user) {
        String query = """
                INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY)
                VALUES (:email, :login, :name, :birthday)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday());
        jdbc.update(query, parameters, keyHolder, new String[]{"USER_ID"});
        Number key = keyHolder.getKey();
        if (key != null) {
            Long userId = key.longValue();
            user.setId(userId);
        } else {
            throw new IllegalStateException("Id пользователя не может быть null");
        }
        return user;
    }

    @Override
    public User update(User user) {
        String query = """
                UPDATE USERS SET EMAIL = :email,
                                 LOGIN = :login,
                                 NAME = :name,
                                 BIRTHDAY = :birthday
                WHERE USER_ID = :id
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday())
                .addValue("id", user.getId());
        jdbc.update(query, parameters);
        return user;
    }
}
