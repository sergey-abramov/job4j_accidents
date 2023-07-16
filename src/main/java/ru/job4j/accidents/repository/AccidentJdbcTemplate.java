package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
@AllArgsConstructor
public class AccidentJdbcTemplate {

    private final JdbcTemplate jdbc;

    private final ResultSetExtractor<Map<Integer, Accident>> extractor = (resultSet) -> {
        Map<Integer, Accident> result = new HashMap<>();
        while (resultSet.next()) {
            Accident accident = new Accident(
                    resultSet.getInt("aId"),
                    resultSet.getString("aName"),
                    resultSet.getString("aDescription"),
                    resultSet.getString("aAddress"),
                    new AccidentType(
                            resultSet.getInt("tId"),
                            resultSet.getString("tName")),
                    new HashSet<>()
            );
            result.putIfAbsent(accident.getId(), accident);
            result.get(accident.getId()).setRules(Set.of(new Rule(
                    resultSet.getInt("rId"),
                    resultSet.getString("rName"))
            ));
        }
        return result;
    };

    private RowMapper<Accident> accidentRowMapper() {
        return (rs, rowNum) -> {
            Accident accident = new Accident();
            accident.setId(rs.getInt("aId"));
            accident.setName(rs.getString("aName"));
            accident.setText(rs.getString("aDescription"));
            accident.setAddress(rs.getString("aAddress"));
            accident.setType(new AccidentType(
                    rs.getInt("tId"),
                    rs.getString("tName")));
            accident.setRules(Set.of(new Rule(
                    rs.getInt("rId"),
                    rs.getString("rName"))));
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
        return Objects.requireNonNull(jdbc.query("""
                        select a.id aId, a.name aName, a.description aDescription,
                        a.address aAddress,t.id tId, t.name tName,
                        r.id rId, r.name rName from accidents a
                        join types t on a.type_id = t.id
                        join rules_accidents ra on a.id = ra.accident_id
                        join rules r on r.id = ra.rule_id""",
                extractor)).values();
    }

    public Optional<Accident> findById(int id) {
        var accident = jdbc.queryForObject("""
                        select a.id aId, a.name aName, a.description aDescription,
                        a.address aAddress,t.id tId, t.name tName,
                        r.id rId, r.name rName from accidents a
                        join types t on a.type_id = t.id
                        join rules_accidents ra on a.id = ra.accident_id
                        join rules r on r.id = ra.rule_id
                        where a.id = ?
                        """,
                accidentRowMapper(), id);
        return Optional.ofNullable(accident);
    }
}
