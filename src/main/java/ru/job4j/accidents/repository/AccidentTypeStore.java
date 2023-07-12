package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

public interface AccidentTypeStore {

    Optional<AccidentType> findById(int id);

    Collection<AccidentType> findAll();
}
