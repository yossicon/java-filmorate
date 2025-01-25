package ru.yandex.practicum.filmorate.storage.friend;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserRowMapper;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendDbStorage {
    private final NamedParameterJdbcOperations jdbc;

    public void addFriend(Long id, Long friendId) {
        String query = """
                INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID)
                VALUES (:userId, :friendId)
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", id)
                .addValue("friendId", friendId);
        jdbc.update(query, parameters);
    }

    public void deleteFriend(Long id, Long friendId) {
        String query = """
                DELETE FROM FRIENDSHIP
                WHERE USER_ID = :userId AND FRIEND_ID = :friendId
                """;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", id)
                .addValue("friendId", friendId);
        jdbc.update(query, parameters);
    }

    public List<User> getUsersFriends(Long id) {
        String query = """
                SELECT FRIEND_ID
                FROM FRIENDSHIP
                WHERE USER_ID = :userId
                """;
        SqlParameterSource parameters = new MapSqlParameterSource("userId", id);
        List<Long> friendsIds = jdbc.queryForList(query, parameters, Long.class);
        List<User> userFriends = new ArrayList<>();
        if (!friendsIds.isEmpty()) {
            String friendsQuery = """
                    SELECT * FROM USERS
                    WHERE USER_ID = :friendId
                    """;
            for (Long friendId : friendsIds) {
                SqlParameterSource idParameters = new MapSqlParameterSource("friendId", friendId);
                userFriends.add(jdbc.queryForObject(friendsQuery, idParameters, new UserRowMapper()));
            }
        }
        return userFriends;
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        List<User> friendsList = getUsersFriends(id);
        List<User> otherFriendsList = getUsersFriends(otherId);
        return friendsList.stream()
                .filter(otherFriendsList::contains)
                .toList();
    }
}
