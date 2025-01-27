package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mpa {
    @NotNull(message = "Mpa id обязателен")
    @Positive(message = "Mpa id должен быть положительным")
    private int id;

    @NotBlank(message = "Название рейтинга не может быть пустым")
    private String name;
}
