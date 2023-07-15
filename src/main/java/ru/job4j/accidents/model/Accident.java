package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accidents")
public class Accident {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "description")
    private String text;
    private String address;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccidentType type;
    @ManyToMany
    @JoinTable(
            name = "rules_accidents",
            joinColumns = { @JoinColumn(name = "rule_id") },
            inverseJoinColumns = { @JoinColumn(name = "accident_id") }
    )
    private Set<Rule> rules = new HashSet<>();
}
