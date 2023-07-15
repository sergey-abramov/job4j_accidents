package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHbmStore {

    private final CrudRepository repository;

    public Optional<AccidentType> findById(int id) {
        return repository.optional("from AccidentType where id = :aId", AccidentType.class,
                Map.of("aId", id));
    }

    public Collection<AccidentType> findAll() {
        return repository.query("from AccidentType", AccidentType.class);
    }
}
