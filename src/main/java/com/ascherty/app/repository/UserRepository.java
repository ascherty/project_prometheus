package com.ascherty.app.repository;

import com.ascherty.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository, base CRUD provided by spring Data JPA.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username); // autogenerate by JPA - SELECT * FROM users WHERE username = ?;

}
