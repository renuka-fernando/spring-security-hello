package com.renuka.repository;

import com.renuka.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);

    Boolean existsByName(String name);
}
