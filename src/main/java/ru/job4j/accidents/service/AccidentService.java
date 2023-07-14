package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService implements ServiceInterface<Accident> {

    private final AccidentRepository store;

    @Override
    public Accident add(Accident model, int[] rIds) {
        return store.save(model);
    }

    @Override
    public void delete(int id) {
        store.deleteById(id);
    }

    @Override
    public boolean update(Accident model, int[] rIds) {
        var a1 = findById(model.getId());
        return !store.save(model).equals(a1.get());
    }

    @Override
    public Iterable<Accident> findALL() {
        return store.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return store.findById(id);
    }
}
