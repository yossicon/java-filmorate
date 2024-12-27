package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public User findById(Long id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return user;
    }

    @Override
    public Collection<User> getUsersFriends(Long id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.getUsersFriends(id);
    }

    @Override
    public Collection<User> getCommonFriends(Long id, Long otherId) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userStorage.findById(otherId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.getCommonFriends(id, otherId);
    }

    @Override
    public User add(User user) {
        return userStorage.add(user);
    }

    @Override
    public User update(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id не указан");
        }
        User oldUser = userStorage.findById(newUser.getId());
        if (oldUser == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", newUser.getId()));
        }
        return userStorage.update(newUser);
    }

    @Override
    public User addFriend(Long id, Long friendId) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userStorage.findById(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.addFriend(id, friendId);
    }

    @Override
    public User deleteFriend(Long id, Long friendId) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        User friend = userStorage.findById(friendId);
        if (friend == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return userStorage.deleteFriend(id, friendId);
    }
}