package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        userService.findById(id);
        return friendDbStorage.getUsersFriends(id);
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        userService.findById(id);
        userService.findById(otherId);
        return friendDbStorage.getCommonFriends(id, otherId);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        userService.findById(id);
        userService.findById(friendId);
        friendDbStorage.addFriend(id, friendId);
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        userService.findById(id);
        userService.findById(friendId);
        friendDbStorage.deleteFriend(id, friendId);
    }
}
