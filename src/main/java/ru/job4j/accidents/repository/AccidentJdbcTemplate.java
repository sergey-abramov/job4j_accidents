package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

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
            accident.setRules(ruleStore.findAllByAccident(rs.getInt("id")));
            return accident;
        };
    }

    public Accident add(Accident accident, int[] rIds) {
        jdbc.update("""
                         insert into accidents (name, description, address, type_id)
                         values (?, ?, ?, ?)
                        """,
        accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId());
        for (Integer i : rIds) {
            jdbc.update("insert into rules_accidents (rule_id, accident_id) values(?, ?)",
                    i, accident.getId());
        }
         return accident;
    }

    public void delete(int id) {
        jdbc.update("delete rules_accidents where accident_id = ?", id);
        jdbc.update("delete accidents where id = ?", id);
    }

    public void update(Accident accident, int[] rIds) {
        jdbc.update("delete rules_accidents where accident_id = ?", accident.getId());
        jdbc.update("""
                        update accidents set name = ?,
                        description = ?, address = ?,
                        type_id = ? where id = ?
                        """,
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        for (Integer i : rIds) {
            jdbc.update("insert into rules_accidents (rule_id, accident_id) values(?, ?)",
                    i, accident.getId());
        }
    }

    public Collection<Accident> findALL() {
        return jdbc.query("select * from accidents",
                         accidentRowMapper());
    }

    public Optional<Accident> findById(int id) {
        var accident = jdbc
                .queryForObject("select * from accidents where id = ?",
                            accidentRowMapper(), id);
        return Optional.ofNullable(accident);
    }
}
