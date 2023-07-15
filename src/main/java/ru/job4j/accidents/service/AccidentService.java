package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentStore;
import ru.job4j.accidents.repository.RuleHbmStore;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService implements ServiceInterface<Accident> {

    private final AccidentRepository store;

    private final RuleHbmStore ruleStore;

    @Override
    public Accident add(Accident model, int[] rIds) {
        List<Rule> listRul = ruleStore.findAll();
        for (Integer i : rIds) {
            model.setRules(Set.of(listRul.get(i)));
        }
        return store.save(model);
    }

    @Override
    public void delete(int id) {
        store.deleteById(id);
    }

    @Override
    public void update(Accident model, int[] rIds) {
        List<Rule> listRul = ruleStore.findAll();
        for (Integer i : rIds) {
            model.setRules(Set.of(listRul.get(i)));
        }
        store.save(model);
    }

    @Override
    public Iterable<Accident> findALL() {
        return store.findAll();
    }

    @Override
    public Optional<Accident> findById(int id) {
        return store.findById(id);
    }
}
