package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Slf4j
@Component
public class UserStorage implements Storage<User> {
    private final Map<Long, User> users = new HashMap<>();
    private final Map<Long, Set<Long>> friends = new HashMap<>();

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public User findById(Long userId) {
        return users.get(userId);
    }

    public Collection<User> getUsersFriends(Long id) {
        friends.putIfAbsent(id, new HashSet<>());
        return friends.get(id).stream()
                .map(this::findById)
                .toList();
    }


    public Collection<User> getCommonFriends(Long id, Long otherId) {
        friends.putIfAbsent(id, new HashSet<>());
        friends.putIfAbsent(otherId, new HashSet<>());
        return friends.get(id).stream()
                .filter(friendId -> friends.get(otherId).contains(friendId))
                .map(this::findById)
                .toList();
    }

    @Override
    public User add(User user) {
        users.forEach((key, value) -> {
            if (user.getEmail().equals(value.getEmail())) {
                throw new DuplicatedDataException(String.format("Email %s уже используется", user.getEmail()));
            }
        });
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextUserId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User newUser) {
        User oldUser = users.get(newUser.getId());
        users.forEach((key, value) -> {
            if (newUser.getEmail().equals(value.getEmail())) {
                throw new DuplicatedDataException(String.format("Email %s уже используется", newUser.getEmail()));
            }
        });
        oldUser.setEmail(newUser.getEmail());
        oldUser.setLogin(newUser.getLogin());
        if (newUser.getName() != null && !newUser.getName().isBlank()) {
            oldUser.setName(newUser.getName());
        } else {
            oldUser.setName(newUser.getLogin());
        }
        if (newUser.getBirthday() != null) {
            oldUser.setBirthday(newUser.getBirthday());
        }
        return oldUser;
    }

    public User addFriend(Long id, Long friendId) {
        friends.putIfAbsent(id, new HashSet<>());
        friends.putIfAbsent(friendId, new HashSet<>());
        if (friends.get(id).contains(friendId) && friends.get(friendId).contains(id)) {
            throw new DuplicatedDataException("Пользователи уже являются друзьями");
        }
        friends.get(id).add(friendId);
        friends.get(friendId).add(id);
        return users.get(id);
    }

    public User deleteFriend(Long id, Long friendId) {
        friends.putIfAbsent(id, new HashSet<>());
        friends.putIfAbsent(friendId, new HashSet<>());
        friends.get(id).remove(friendId);
        friends.get(friendId).remove(id);
        return users.get(id);
    }

    private long getNextUserId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
