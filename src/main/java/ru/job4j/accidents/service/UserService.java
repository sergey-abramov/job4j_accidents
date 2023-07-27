package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Optional<User> save(User user) {
        try {
            return Optional.of(repository.save(user));
        } catch (ConstraintViolationException e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmailAndPassword(String username, String password) {
        for (User u : repository.findAll()) {
            if (u.getPassword().equals(password) && u.getUsername().equals(username)) {
                return Optional.of(u);
            }
        }
        return repository.findByUsernameAndPassword(username, password);
    }

}
