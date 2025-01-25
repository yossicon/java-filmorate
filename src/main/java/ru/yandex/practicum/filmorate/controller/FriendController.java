package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("users/{id}/friends")
@RequiredArgsConstructor
@Slf4j
public class FriendController {
    private final FriendService friendService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersFriends(@PathVariable Long id) {
        log.info("Получение друзей пользователя с id {}", id);
        return friendService.getUsersFriends(id);
    }

    @GetMapping("/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получение друзей пользователя с id {}, {}", id, otherId);
        return friendService.getCommonFriends(id, otherId);
    }

    @PutMapping("/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Добавление пользователем с id {} пользователя с id {} в друзья ", id, friendId);
        friendService.addFriend(id, friendId);
        log.info("Пользователь с id {} успешно добавлен в друзья пользователю с id {}", friendId, id);
    }

    @DeleteMapping("/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Удаление пользователем с id {} пользователя с id {} из друзей ", id, friendId);
        friendService.deleteFriend(id, friendId);
        log.info("Пользователь с id {} успешно удалён из друзей пользователя с id {}", friendId, id);
    }
}
