package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentStore {

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, Accident> repository = new ConcurrentHashMap<>();

    public AccidentMem() {
        add(new Accident(1, "Number 1", "test", "Moscow"));
        add(new Accident(1, "Number 2", "test", "Moscow"));
        add(new Accident(1, "Number 2", "test", "Moscow"));
    }

    @Override
    public Accident add(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        return repository.put(accident.getId(), accident);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public boolean update(Accident accident) {
        return repository.computeIfPresent(accident.getId(),
                (id, oldAccident) -> new Accident(
                        accident.getId(), accident.getName(),
                        accident.getText(), accident.getAddress())) != null;
    }

    @Override
    public Collection<Accident> findALL() {
        return repository.values();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(repository.get(id));
    }
}
