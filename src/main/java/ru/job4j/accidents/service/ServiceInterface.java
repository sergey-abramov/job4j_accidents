package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface ServiceInterface<T> {

    T add(T model);

    boolean delete(int id);

    boolean update(T model);

    Collection<T> findALL();

    Optional<T> findById(int id);
}
