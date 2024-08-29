package com.deviget.minesweeper.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deviget.minesweeper.model.User;

/**
 * Repository class for {@link User} persistence.
 * 
 * @author david.rios
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user with the given user name.
     * 
     * @param username - the user name.
     * @return an optional {@link User} instance.
     */
    Optional<User> findByUsername(String username);

    /**
     * Validates a user exists for the given user name.
     * 
     * @param username - the user name.
     * @return true if a user exists, false otherwise.
     */
    Boolean existsByUsername(String username);

    /**
     * Validates a user exists for the given email.
     * 
     * @param email - the user email.
     * @return true if a user exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}
