package ru.yandex.practicum.filmorate.service;

public interface LikeService {
    void addLike(Long id, Long userId);

    void deleteLike(Long id, Long userId);
}
