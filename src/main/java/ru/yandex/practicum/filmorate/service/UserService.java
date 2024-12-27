package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    Collection<User> findAll();

    User findById(Long userId);

    Collection<User> getUsersFriends(Long id);

    User add(User user);

    User update(User user);

    Collection<User> getCommonFriends(Long id, Long otherId);

    User addFriend(Long id, Long friendId);

    User deleteFriend(Long id, Long friendId);
}
