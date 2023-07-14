package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class AccidentTypeJdbcStore {

    private final JdbcTemplate jdbc;

    private final RowMapper<AccidentType> typeRowMapper = (rs, rowNum) -> {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(rs.getInt("id"));
        accidentType.setName(rs.getString("name"));
        return accidentType;
    };

    public Optional<AccidentType> findById(int id) {
         return Optional.ofNullable(jdbc
                .queryForObject("select * from types where id = ?", typeRowMapper, id));
    }

    public Collection<AccidentType> findAll() {
        return jdbc.query("select * from types", typeRowMapper);
    }
}
