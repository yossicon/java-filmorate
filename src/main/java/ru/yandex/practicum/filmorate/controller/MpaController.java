package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@RequestMapping("mpa")
@RequiredArgsConstructor
@Slf4j
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Mpa> findAllMpa() {
        log.info("Получение всех рейтингов");
        return mpaService.findAllMpa();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mpa findMpaById(@PathVariable Integer id) {
        log.info("Получение рейтинга по id {}", id);
        return mpaService.findMpaById(id);
    }
}
