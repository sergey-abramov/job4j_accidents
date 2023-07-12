package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleJdbcStore;

import java.util.Collection;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleJdbcStore store;

    public Collection<Rule> findAll() {
        return store.findAll();
    }

    public Set<Rule> findAllByAccident(int id) {
        return store.findAllByAccident(id);
    }
}
