package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({MpaDbStorage.class})
public class MpaDbStorageTest {
    private final MpaDbStorage mpaDbStorage;

    @Test
    public void testFindAllMpa() {
        List<Mpa> ratings = mpaDbStorage.findAllMpa();

        assertThat(ratings)
                .isNotNull()
                .hasSize(5);
    }

    @Test
    public void testFindMpaById() {
        Optional<Mpa> mpaOptional = mpaDbStorage.findMpaById(1);

        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> assertThat(mpa)
                        .hasFieldOrPropertyWithValue("id", 1)
                );
    }
}
