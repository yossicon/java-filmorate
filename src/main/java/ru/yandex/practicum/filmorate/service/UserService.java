package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User add(User user);

    User update(User user);
}
