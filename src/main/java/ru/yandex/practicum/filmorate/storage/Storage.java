package ru.yandex.practicum.filmorate.storage;

import java.util.Collection;

public interface Storage<T> {
    Collection<T> findAll();

    T findById(Long id);

    T add(T t);

    T update(T t);
}
