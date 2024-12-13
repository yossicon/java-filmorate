package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserTest {

    Validator validator;
    User user;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
        user = new User();
        user.setEmail("user@gmail.com");
        user.setLogin("Login");
        user.setName("Name");
        user.setBirthday(LocalDate.EPOCH);
    }

    @Test
    public void shouldPassWhenAllValidFields() {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Не все поля корректны");
    }

    @Test
    public void shouldFailWhenAllFieldsEmpty() {
        User user1 = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user1);
        assertFalse(violations.isEmpty(), "Пользователь с пустыми полями не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenZeroId() {
        user.setId(0L);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Нулевой  id не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenNegativeId() {
        user.setId(-1L);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Отрицательный id не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenEmptyEmail() {
        user.setEmail(" ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(2, violations.size(), "Пустой email не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenBadFormatEmail() {
        user.setEmail("mail.ru");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Неверный формат email не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenEmptyLogin() {
        user.setLogin(" ");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(2, violations.size(), "Пустой логин не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenSpacesInLogin() {
        user.setLogin("Login log in");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Логин с пробелами не должен проходить валидацию");
    }

    @Test
    public void shouldFailWhenBirthDateInFuture() {
        user.setBirthday(LocalDate.MAX);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "День рождения в будущем не должна проходить валидацию");
    }
}
