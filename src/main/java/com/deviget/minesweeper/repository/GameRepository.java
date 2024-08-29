package com.deviget.minesweeper.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.deviget.minesweeper.model.Game;

/**
 * Repository class for {@link Game} persistence.
 * 
 * @author david.rios
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    /**
     * Retrieves a list of games associated to the provided user name.
     * 
     * @param username - the user name.
     * @return a {@link List} of {@link Game} instances.
     */
    List<Game> findAllByUsername(String username);
}
