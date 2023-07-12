package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService implements ServiceInterface<Accident> {

    private final AccidentStore store;

    @Override
    public Accident add(Accident model, int[] rIds) {
        return store.add(model, rIds);
    }

    @Override
    public boolean delete(int id) {
        return store.delete(id);
    }

    @Override
    public boolean update(Accident model, int[] rIds) {
        return store.update(model, rIds);
    }

    @Override
    public Collection<Accident> findALL() {
        return store.findALL();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return store.findById(id);
    }
}
