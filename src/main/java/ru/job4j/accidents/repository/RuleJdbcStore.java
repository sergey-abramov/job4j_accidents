package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;

@AllArgsConstructor
public class RuleJdbcStore {

    private final JdbcTemplate jdbc;

    private final RowMapper<Rule> mapper = ((rs, rowNum) -> {
       Rule rule = new Rule();
       rule.setId(rs.getInt("id"));
       rule.setName(rs.getString("name"));
       return rule;
    });

    public Collection<Rule> findAll() {
        return jdbc.query("select * from rules", mapper);
    }
}
