package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Получение всех пользователей");
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(Long id) {
        log.info("Получение пользователя по id {}", id);
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getUsersFriends(@PathVariable Long id) {
        log.info("Получение друзей пользователя с id {}", id);
        return userService.getUsersFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получение друзей пользователя с id {}, {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Добавление пользователя {}", user.getLogin());
        User addedUser = userService.addUser(user);
        log.info("Пользователь {} успешно добавлен с id {}", addedUser.getLogin(), addedUser.getId());
        return addedUser;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User newUser) {
        log.info("Обновление данных пользователя {}", newUser.getLogin());
        User updatedUser = userService.updateUser(newUser);
        log.info("Данные пользователя {} с id {} успешно обновлены", updatedUser.getLogin(), updatedUser.getId());
        return updatedUser;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Добавление пользователем с id {} пользователя с id {} в друзья ", id, friendId);
        User user = userService.addFriend(id, friendId);
        log.info("Пользователь с id {} успешно добавлен в друзья пользователю с id {}", friendId, id);
        return user;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Удаление пользователем с id {} пользователя с id {} из друзей ", id, friendId);
        User user = userService.deleteFriend(id, friendId);
        log.info("Пользователь с id {} успешно удалён из друзей пользователя с id {}", friendId, id);
        return user;
    }
}
