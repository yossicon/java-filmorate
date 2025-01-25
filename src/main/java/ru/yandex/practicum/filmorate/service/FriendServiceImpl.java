package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
    private final UserService userService;
    private final FriendDbStorage friendDbStorage;

    @Override
    public List<User> getUsersFriends(Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return friendDbStorage.getUsersFriends(id);
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userService.findById(otherId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return friendDbStorage.getCommonFriends(id, otherId);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userService.findById(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        friendDbStorage.addFriend(id, friendId);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userService.findById(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        friendDbStorage.deleteFriend(id, friendId);
    }
}
