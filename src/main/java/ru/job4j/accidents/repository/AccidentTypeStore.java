package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

public interface AccidentTypeStore extends CrudRepository<AccidentType, Integer> {

    List<AccidentType> findAll();

}
