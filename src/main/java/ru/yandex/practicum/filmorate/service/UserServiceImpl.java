package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDbStorage userDbStorage;

    @Override
    public List<User> findAll() {
        return userDbStorage.findAll();
    }

    @Override
    public User findById(Long id) {
        User user = userDbStorage.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", id));
        }
        return user;
    }

    @Override
    public User add(User user) {
        return userDbStorage.add(user);
    }

    @Override
    public User update(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id не указан");
        }
        User oldUser = userDbStorage.findById(newUser.getId());
        if (oldUser == null) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", newUser.getId()));
        }
        return userDbStorage.update(newUser);
    }
}