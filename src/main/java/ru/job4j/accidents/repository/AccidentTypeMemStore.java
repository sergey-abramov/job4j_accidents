package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AccidentTypeMemStore implements AccidentTypeStore {

    private final List<AccidentType> types = List.of(new AccidentType(0, "Две машины"),
            new AccidentType(1, "Машина и человек"), new AccidentType(2, "Машина и велосипед"));

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types;
    }
}
