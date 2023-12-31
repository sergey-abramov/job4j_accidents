package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface ServiceInterface<T> {

    T add(T model);

    void delete(int id);

    void update(T model);

    Iterable<T> findALL();

    Optional<T> findById(int id);
}
