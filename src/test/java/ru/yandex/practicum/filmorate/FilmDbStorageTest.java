package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({FilmDbStorage.class, LikeDbStorage.class, MpaDbStorage.class})
public class FilmDbStorageTest {
    private final FilmDbStorage filmDbStorage;
    private final LikeDbStorage likeDbStorage;
    private final MpaDbStorage mpaDbStorage;
    Film film;

    @BeforeEach
    public void setUp() {
        film = new Film();
        film.setName("film_name");
        film.setDescription("film_description");
        film.setReleaseDate(LocalDate.EPOCH);
        film.setDuration(180L);
        film.setMpa(mpaDbStorage.findMpaById(1));
    }

    @Test
    public void testFindAllFilms() {
        List<Film> films = filmDbStorage.findAll();

        assertThat(films)
                .isNotNull()
                .hasSize(2);
    }

    @Test
    public void testFindFilmById() {
        Film foundedFilm = filmDbStorage.findById(1L);

        assertThat(foundedFilm)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L);
    }

    @Test
    public void testAddFilm() {
        Film addedFilm = filmDbStorage.add(film);

        assertThat(addedFilm)
                .isNotNull()
                .isEqualTo(filmDbStorage.findById(addedFilm.getId()));
        assertThat(addedFilm.getName()).isEqualTo("film_name");
        assertThat(addedFilm.getDescription()).isEqualTo("film_description");
        assertThat(addedFilm.getReleaseDate()).isEqualTo(LocalDate.EPOCH);
        assertThat(addedFilm.getDuration()).isEqualTo(180L);
    }

    @Test
    public void testUpdateFilm() {
        Film addedFilm = filmDbStorage.add(film);
        addedFilm.setName("new_name");
        addedFilm.setDescription("new_description");
        Film updatedFilm = filmDbStorage.update(film);

        assertThat(updatedFilm)
                .isNotNull()
                .isEqualTo(filmDbStorage.findById(addedFilm.getId()));
        assertThat(updatedFilm.getName()).isEqualTo("new_name");
        assertThat(updatedFilm.getDescription()).isEqualTo("new_description");
    }

    @Test
    public void testGetPopularFilms() {
        likeDbStorage.addLike(1L, 1L);
        likeDbStorage.addLike(2L, 1L);
        likeDbStorage.addLike(2L, 2L);
        List<Film> expectedPopularFilms = new ArrayList<>();
        expectedPopularFilms.add(filmDbStorage.findById(2L));
        expectedPopularFilms.add(filmDbStorage.findById(1L));
        List<Film> popularFilms = filmDbStorage.getMostPopular(2L);

        assertThat(popularFilms)
                .isNotNull()
                .hasSize(2)
                .isEqualTo(expectedPopularFilms);
    }
}
