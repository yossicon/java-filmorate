package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.NoSpaces;

import java.time.LocalDate;

/**
 * User.
 */
@Data
public class User {

    @Positive(message = "id должен быть положительным")
    private Long id;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен соответсвовать формату")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    @NoSpaces
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
