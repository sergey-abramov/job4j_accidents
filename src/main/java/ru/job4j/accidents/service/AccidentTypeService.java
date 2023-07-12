package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeJdbcStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeJdbcStore store;

    public Collection<AccidentType> findAll() {
        return store.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return store.findById(id);
    }
}
