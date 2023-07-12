package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentStore {

    private final JdbcTemplate jdbc;

    private final AtomicInteger nextId = new AtomicInteger(0);

    private final AccidentTypeJdbcStore typeStore;

    private final RuleJdbcStore ruleStore;

    private RowMapper<Accident> accidentRowMapper() {
        return (rs, rowNum) -> {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id"));
            accident.setName(rs.getString("name"));
            accident.setText(rs.getString("description"));
            accident.setAddress(rs.getString("address"));
            var type = typeStore.findById(rs.getInt("type_id"));
            type.ifPresent(accident::setType);
            return accident;
        };
    }

    @Override
    public Accident add(Accident accident, int[] rIds) {
        var c = nextId.incrementAndGet();
        jdbc.update("""
                         insert into accidents (name, description, address, type_id)
                         values (?, ?, ?, ?)
                        """,
        accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId());
        for (Integer i : rIds) {
            jdbc.update("insert into rules_accidents (rule_id, accident_id) values(?, ?)",
                    i, c);
        }
         return accident;
    }

    @Override
    public boolean delete(int id) {
        jdbc.update("delete rules_accidents where accident_id = ?", id);
        jdbc.update("delete accidents where id = ?", id);
        return false;
    }

    @Override
    public boolean update(Accident accident, int[] rIds) {
        jdbc.update("""
                        update accidents set name = ?,
                        description = ?, address = ?,
                        type_id = ? where id = ?
                        """,
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        for (Integer i : rIds) {
            jdbc.update("update rules_accidents set rule_id = ? where accident_id = ?",
                    i, accident.getId());
        }
        return false;
    }

    @Override
    public Collection<Accident> findALL() {
        var accidents = jdbc.query("select * from accidents",
                         accidentRowMapper());
        for (Accident a : accidents) {
            a.setRules(ruleStore.findAllByAccident(a.getId()));
        }
        return accidents;
    }

    @Override
    public Optional<Accident> findById(int id) {
        var accident = jdbc
                .queryForObject("select * from accidents where id = ?",
                            accidentRowMapper(), id);
        if (accident != null) {
            accident.setRules(ruleStore.findAllByAccident(id));
        }
        return Optional.ofNullable(accident);
    }
}
