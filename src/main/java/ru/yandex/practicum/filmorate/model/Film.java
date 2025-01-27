package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinDate;

import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {

    @Positive(message = "id фильма должен быть положительным")
    private Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;

    @PastOrPresent(message = "Дата релиза не может быть в будущем")
    @MinDate(value = "1895-12-28")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;

    private Set<Genre> genres;

    @NotNull(message = "Mpa рейтинг обязателен")
    private Mpa mpa;
}
