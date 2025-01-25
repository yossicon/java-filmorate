package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({GenreDbStorage.class})
public class GenreDbStorageTest {
    private final GenreDbStorage genreDbStorage;

    @Test
    public void testFindAllGenres() {
        List<Genre> genres = genreDbStorage.findAllGenres();

        assertThat(genres)
                .isNotNull()
                .hasSize(6);
    }

    @Test
    public void testFindGenreById() {
        Genre foundedGenre = genreDbStorage.findGenreById(1);

        assertThat(foundedGenre)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1);
    }
}
