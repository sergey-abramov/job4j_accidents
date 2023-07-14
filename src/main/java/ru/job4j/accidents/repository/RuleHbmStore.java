package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleHbmStore {

    private final SessionFactory sf;

    public Collection<Rule> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class).list();
        }
    }

    public Set<Rule> findAllByAccident(int id) {
        return null;
    }
}
