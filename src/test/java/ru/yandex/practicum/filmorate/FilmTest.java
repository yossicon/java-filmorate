package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FilmTest {

    Validator validator;
    Film film;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
        film = new Film();
        film.setName("Film");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.EPOCH);
        film.setDuration(120L);
    }

    @Test
    public void shouldPassWhenAllValidFields() {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(0, violations.size(), "Не все поля корректны");
    }

    @Test
    public void shouldFailWhenAllFieldsEmpty() {
        Film film1 = new Film();
        Set<ConstraintViolation<Film>> violations = validator.validate(film1);
        assertFalse(violations.isEmpty(), "Фильм с пустыми полями не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenZeroId() {
        film.setId(0L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Нулевой  id не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenNegativeId() {
        film.setId(-1L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Отрицательный id не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenEmptyName() {
        film.setName(" ");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Пустое имя не должно проходить валидацию");
    }

    @Test
    public void shouldFailWhenEmptyDescription() {
        film.setDescription(" ");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Пустое описание не должно проходить валидацию");
    }

    @Test
    public void shouldFailWhenDescriptionLengthMoreThan200() {
        film.setDescription("Dead Man’s Chest is an adventure movie with lots of action. It is also fantasy " +
                "because it includes magical and impossible things that no real human being can do. " +
                "It is a sequel to The Curse of the Black Pearl. " +
                "Jack Sparrow, the captain of the Black Pearl, is back with new adventures.");
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Описание >200 символов не должно проходить валидацию");
    }

    @Test
    public void shouldFailWhenReleaseDateInFuture() {
        film.setReleaseDate(LocalDate.MAX);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Дата релиза в будущем не должна проходить валидацию");
    }

    @Test
    public void shouldFailWhenReleaseDateBeforeMinDate() {
        film.setReleaseDate(LocalDate.of(1800, 1, 1));
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Дата релиза раньше 1895-12-28 не должна проходить валидацию");
    }

    @Test
    public void shouldFailWhenZeroDuration() {
        film.setDuration(0L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Нулевая продолжительность не должна проходить валидацию");
    }

    @Test
    public void shouldFailWhenNegativeDuration() {
        film.setDuration(-1L);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Отрицательная продолжительность не должна проходить валидацию");
    }
}
