package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface ServiceInterface<T> {

    T add(T model, int[] rIds);

    void delete(int id);

    boolean update(T model, int[] rIds);

    Iterable<T> findALL();

    Optional<T> findById(int id);
}
