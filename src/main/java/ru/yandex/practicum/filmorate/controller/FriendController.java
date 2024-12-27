package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping("users/{id}/friends")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getUsersFriends(@PathVariable Long id) {
        log.info("Получение друзей пользователя с id {}", id);
        return userService.getUsersFriends(id);
    }

    @GetMapping("/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получение друзей пользователя с id {}, {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public User addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Добавление пользователем с id {} пользователя с id {} в друзья ", id, friendId);
        User user = userService.addFriend(id, friendId);
        log.info("Пользователь с id {} успешно добавлен в друзья пользователю с id {}", friendId, id);
        return user;
    }

    @DeleteMapping("/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public User deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Удаление пользователем с id {} пользователя с id {} из друзей ", id, friendId);
        User user = userService.deleteFriend(id, friendId);
        log.info("Пользователь с id {} успешно удалён из друзей пользователя с id {}", friendId, id);
        return user;
    }
}
