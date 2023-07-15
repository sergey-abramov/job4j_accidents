package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleStore;

import java.util.List;

@Service
@AllArgsConstructor
public class RuleService {

    private final RuleStore store;

    public List<Rule> findAll() {
        return store.findAll();
    }

}
