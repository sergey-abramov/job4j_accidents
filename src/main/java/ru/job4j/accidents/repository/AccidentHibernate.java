package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class AccidentHibernate implements AccidentStore {

    private final CrudRepository repository;

    @Override
    public Accident add(Accident accident) {
        repository.run(session -> session.persist(accident));
        return accident;
    }

    @Override
    public boolean delete(int id) {
        return repository.booleanRun("delete from Accident where id = :aId", Map.of("aId", id));
    }

    @Override
    public void update(Accident accident) {
        repository.run(session -> session.merge(accident));
    }

    @Override
    public Collection<Accident> findALL() {
        return repository.query("from Accident", Accident.class);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.optional("from Accident where id = :aId", Accident.class,
                Map.of("aId", id));

    }
}
