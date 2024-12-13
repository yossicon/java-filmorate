package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.MinDate;

import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {

    @Positive(message = "id должен быть положительным")
    private Long id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @NotBlank(message = "Описание фильма не может быть пустым")
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;

    @NotNull(message = "Дата релиза должна быть заполнена")
    @Past
    @MinDate(value = "1895-12-28")
    private LocalDate releaseDate;

    @NotNull(message = "Продолжительность фильма должна быть заполнена")
    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    private Long duration;
}
