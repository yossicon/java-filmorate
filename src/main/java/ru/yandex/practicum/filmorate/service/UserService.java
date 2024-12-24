package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(Long id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return user;
    }

    public Collection<User> getUsersFriends(Long id) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.getUsersFriends(id);
    }

    public Collection<User> getCommonFriends(Long id, Long otherId) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userStorage.getUserById(otherId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.getCommonFriends(id, otherId);
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id не указан");
        }
        User oldUser = userStorage.getUserById(newUser.getId());
        if (oldUser == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", newUser.getId()));
        }
        return userStorage.updateUser(newUser);
    }

    public User addFriend(Long id, Long friendId) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userStorage.getUserById(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.addFriend(id, friendId);
    }

    public User deleteFriend(Long id, Long friendId) {
        User user = userStorage.getUserById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userStorage.getUserById(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.deleteFriend(id, friendId);
    }
}