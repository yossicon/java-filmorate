package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaServiceImpl implements MpaService {
    private final MpaDbStorage mpaDbStorage;

    @Override
    public List<Mpa> findAllMpa() {
        return mpaDbStorage.findAllMpa();
    }

    @Override
    public Mpa findMpaById(Integer id) {
        return mpaDbStorage.findMpaById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Рейтинг с id %d не найден", id)));
    }
}
