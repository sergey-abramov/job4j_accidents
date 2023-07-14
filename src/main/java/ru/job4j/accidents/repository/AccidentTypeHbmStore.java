package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHbmStore implements AccidentTypeStore {

    private final SessionFactory sf;

    @Override
    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType where id = :aId", AccidentType.class)
                    .setParameter("aId", id)
                    .uniqueResultOptional();
        }
    }

    @Override
    public Collection<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }
}
