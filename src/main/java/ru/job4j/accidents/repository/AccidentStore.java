package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentStore {

    Accident add(Accident accident, int[] rIds);

    boolean delete(int id);

    boolean update(Accident accident, int[] rIds);

    Collection<Accident> findALL();

    Optional<Accident> findById(int id);
}
