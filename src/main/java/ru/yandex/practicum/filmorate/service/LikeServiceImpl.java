package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.like.LikeDbStorage;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final FilmService filmService;
    private final UserService userService;
    private final LikeDbStorage likeDbStorage;

    @Override
    public void addLike(Long id, Long userId) {
        filmService.findById(id);
        userService.findById(userId);
        likeDbStorage.addLike(id, userId);
    }

    @Override
    public void deleteLike(Long id, Long userId) {
        filmService.findById(id);
        userService.findById(userId);
        likeDbStorage.deleteLike(id, userId);
    }
}
