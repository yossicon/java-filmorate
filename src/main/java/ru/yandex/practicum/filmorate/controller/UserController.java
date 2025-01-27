package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        log.info("Получение всех пользователей");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable Long id) {
        log.info("Получение пользователя по id {}", id);
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавление пользователя {}", user.getLogin());
        User addedUser = userService.add(user);
        log.info("Пользователь {} успешно добавлен с id {}", addedUser.getLogin(), addedUser.getId());
        return addedUser;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Обновление данных пользователя {}", newUser.getLogin());
        User updatedUser = userService.update(newUser);
        log.info("Данные пользователя {} с id {} успешно обновлены", updatedUser.getLogin(), updatedUser.getId());
        return updatedUser;
    }
}
