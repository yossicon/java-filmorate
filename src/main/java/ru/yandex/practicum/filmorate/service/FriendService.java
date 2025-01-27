package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendService {
    List<User> getUsersFriends(Long id);

    List<User> getCommonFriends(Long id, Long otherId);

    void addFriend(Long id, Long friendId);

    void deleteFriend(Long id, Long friendId);
}
