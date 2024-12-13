package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавление пользователя {}", user.getLogin());
        users.forEach((key, value) -> {
            if (user.getEmail().equals(value.getEmail())) {
                log.warn("Email {} уже используется", user.getEmail());
                throw new DuplicatedDataException("Email уже используется");
            }
        });
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getNextUserId());
        users.put(user.getId(), user);
        log.info("Пользователь {} успешно добавлен с id {}", user.getLogin(), user.getId());
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Обновление данных пользователя {}", newUser.getLogin());
        if (newUser.getId() == null) {
            log.warn("Id не указан");
            throw new ConditionsNotMetException("Id не указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            users.forEach((key, value) -> {
                if (newUser.getEmail().equals(value.getEmail())) {
                    log.warn("Email {} уже используется, email {} не может быть обновлён",
                            newUser.getEmail(), oldUser.getEmail());
                    throw new DuplicatedDataException("Email уже используется");
                }
            });
            oldUser.setEmail(newUser.getEmail());
            oldUser.setLogin(newUser.getLogin());
            if (newUser.getName() != null) {
                oldUser.setName(newUser.getName());
            } else {
                oldUser.setName(newUser.getLogin());
            }
            oldUser.setBirthday(newUser.getBirthday());
            log.info("Данные пользователя {} с id {} успешно обновлены", oldUser.getLogin(), oldUser.getId());
            return oldUser;
        }
        log.warn("Пользователь с id = {} не найден", newUser.getId());
        throw new NotFoundException("Пользователь не найден");
    }

    private long getNextUserId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
