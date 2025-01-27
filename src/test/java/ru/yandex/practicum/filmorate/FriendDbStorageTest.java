package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FriendDbStorage.class, UserDbStorage.class})
public class FriendDbStorageTest {
    private final FriendDbStorage friendDbStorage;
    private final UserDbStorage userDbStorage;

    @Test
    public void testGetUsersFriends() {
        List<User> expectedUsersFriends = new ArrayList<>();
        expectedUsersFriends.add(userDbStorage.findById(1L).get());
        expectedUsersFriends.add(userDbStorage.findById(4L).get());
        List<User> friends = friendDbStorage.getUsersFriends(3L);

        assertThat(friends)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedUsersFriends);
    }

    @Test
    public void testGetCommonFriends() {
        List<User> expectedCommonFriends = new ArrayList<>();
        expectedCommonFriends.add(userDbStorage.findById(1L).get());
        expectedCommonFriends.add(userDbStorage.findById(4L).get());
        List<User> friends = friendDbStorage.getCommonFriends(2L, 3L);

        assertThat(friends)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedCommonFriends);
    }

    @Test
    public void testAddFriend() {
        friendDbStorage.addFriend(1L, 2L);

        assertThat(friendDbStorage.getUsersFriends(1L))
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    public void testDeleteFriend() {
        friendDbStorage.addFriend(1L, 2L);

        assertThat(friendDbStorage.getUsersFriends(1L))
                .isNotEmpty()
                .hasSize(1);

        friendDbStorage.deleteFriend(1L, 2L);

        assertThat(friendDbStorage.getUsersFriends(1L))
                .isEmpty();
    }
}
