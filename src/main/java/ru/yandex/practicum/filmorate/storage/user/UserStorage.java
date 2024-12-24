package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> getUsers();

    User getUserById(Long userId);

    Collection<User> getUsersFriends(Long id);

    User addUser(User user);

    User updateUser(User user);

    Collection<User> getCommonFriends(Long id, Long otherId);

    User addFriend(Long id, Long friendId);

    User deleteFriend(Long id, Long friendId);
}
