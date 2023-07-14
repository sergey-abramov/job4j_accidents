package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentStore {

    private final SessionFactory sf;

    @Override
    public Accident add(Accident accident, int[] rIds) {
        try (Session session = sf.openSession()) {
            accident.setRules(Set.of());
            session.save(accident);
            return accident;
        }
    }

    @Override
    public boolean delete(int id) {
        boolean rsl;
        try (Session session = sf.openSession()) {
            rsl = session.createQuery("delete from Accident where id = :aId")
                    .setParameter("aId", id)
                    .executeUpdate() > 0;
        }
        return rsl;
    }

    @Override
    public boolean update(Accident accident, int[] rIds) {
        var a1 = findById(accident.getId());
        boolean rsl;
        try (Session session = sf.openSession()) {
            rsl = !a1.get().equals(session.merge(accident));
        }
        return rsl;
    }

    @Override
    public Collection<Accident> findALL() {
        try (Session session = sf.openSession()) {
           return session.createQuery("from Accident", Accident.class)
                    .list();
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Accident where id = :aId", Accident.class)
                    .setParameter("aId", id)
                    .uniqueResultOptional();
        }
    }
}
