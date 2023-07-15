package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class RuleMemStore {

    private final Map<Integer, Rule> rules = Map.of(
            1, new Rule(1, "Статья. 1"),
            2, new Rule(2, "Статья. 2"),
            3, new Rule(3, "Статья. 3")
    );

    public Collection<Rule> findAll() {
        return rules.values();
    }
}
