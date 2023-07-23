package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class RuleHbmStore {

    private final CrudRepository repository;

    public List<Rule> findAll() {
        return repository.query("from Rule", Rule.class);
    }

}
