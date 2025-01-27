package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserDbStorage.class})
class UserDbStorageTest {
    private final UserDbStorage userDbStorage;
    User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail("user@gmail.com");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.EPOCH);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userDbStorage.findAll();

        assertThat(users)
                .isNotNull()
                .hasSize(4);
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOptional = userDbStorage.findById(1L);

        assertThat(userOptional)
                .isNotNull()
                .hasValueSatisfying(user -> assertThat(user)
                        .hasFieldOrPropertyWithValue("id", 1L)
                );
    }

    @Test
    public void testAddUser() {
        User addedUser = userDbStorage.add(user);

        assertThat(addedUser)
                .isNotNull()
                .isEqualTo(userDbStorage.findById(addedUser.getId()).get());
        assertThat(addedUser.getId()).isNotNull();
        assertThat(addedUser.getEmail()).isEqualTo("user@gmail.com");
        assertThat(addedUser.getLogin()).isEqualTo("login");
        assertThat(addedUser.getName()).isEqualTo("name");
        assertThat(addedUser.getBirthday()).isEqualTo(LocalDate.EPOCH);
    }

    @Test
    public void testUpdateUser() {
        User addedUser = userDbStorage.add(user);
        addedUser.setEmail("new_email@gmail.com");
        addedUser.setLogin("new_login");
        User updatedUser = userDbStorage.update(addedUser);

        assertThat(updatedUser)
                .isNotNull()
                .isEqualTo(userDbStorage.findById(addedUser.getId()).get());
        assertThat(updatedUser.getEmail()).isEqualTo("new_email@gmail.com");
        assertThat(updatedUser.getLogin()).isEqualTo("new_login");
    }
}

