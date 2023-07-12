package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class RuleJdbcStore implements RuleStore {

    private final JdbcTemplate jdbc;

    private final RowMapper<Rule> mapper = ((rs, rowNum) -> {
       Rule rule = new Rule();
       rule.setId(rs.getInt("id"));
       rule.setName(rs.getString("name"));
       return rule;
    });

    @Override
    public Set<Rule> findAllByAccident(int id) {
        return jdbc.query("""
                select r.id, name from rules r
                join rules_accidents ra on r.id = ra.rule_id
                where accident_id = ?
                """, mapper, id).stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("select * from rules", mapper);
    }
}
