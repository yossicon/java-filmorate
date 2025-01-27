package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface Storage<T> {
    List<T> findAll();

    Optional<T> findById(Long id);

    T add(T t);

    T update(T t);
}
