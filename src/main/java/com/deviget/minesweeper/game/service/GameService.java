package com.deviget.minesweeper.game.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.model.GameStatus;
import com.deviget.minesweeper.payload.dto.GameDto;
import com.deviget.minesweeper.payload.response.MessageResponse;
import com.deviget.minesweeper.repository.GameRepository;

/**
 * Service class implementing all operations for {@link Game} management.
 *
 * @author david.rios
 */
@Service
@Transactional
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * Interacts with {@link GameRepository} to create a new game associated to the current user.
     *
     * @param request - a {@link GameDto} instance with the game details.
     */
    public Game saveGame(GameDto gameDto) {
        Game game;
        Long id = gameDto.getId();
        if (id != null) {
            game = gameRepository.findById(id)
                    .orElseThrow(() -> new GameNotFoundException("Game Not Found with id: " + id));
            // rowRepository.deleteAllByGame(game);
            game.setRowsFromCellArray(gameDto.getCells());
            game.setRemainingCells(gameDto.getRemainingCells());
            game.setTimer(gameDto.getTimer());
            game.setStatus(GameStatus.valueOf(gameDto.getStatus()));
            // Delete existing rows and cells because we're losing the id's.
            // rowRepository.deleteAllByGame(game);
        } else {
            game = GameDto.toGame(gameDto);
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            game.setUsername(username);
        }
        game.setLastUpdated(LocalDateTime.now());
        gameRepository.save(game);
        return game;
    }

    /**
     * Interacts with {@link GameRepository} to get all games for current user.
     *
     * @return a {@link List} of {@link Game} instances associated to the authenticated user.
     */
    public List<Game> getAllGamesForCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        List<Game> games = gameRepository.findAllByUsername(username);
        return games;
    }

    /**
     * Interacts with {@link GameRepository} to get an individual game.
     *
     * @param id - the game id.
     * @return a {@link Game} instance if the game is associated to the authenticated user, an error otherwise.
     */
    public Game getGame(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game Not Found with id: " + id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        if (!username.equals(game.getUsername())) {
            throw new GameNotAvailableException("Game Not Available for current user: " + username);
        }
        return game;
    }

    /**
     * Interacts with {@link GameRepository} to delete an individual game associated to the current user.
     *
     * @param id - the game id.
     * @return a {@link MessageResponse} indicating if the deletion was successful or not.
     */
    public void deleteGame(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException("Game Not Found with id: " + id));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        if (!username.equals(game.getUsername())) {
            throw new GameNotAvailableException("Game Not Available for user: " + username);
        }
        gameRepository.delete(game);
    }

    /**
     * Interacts with {@link GameRepository} to delete all games associated to the current user.
     *
     * @return a {@link MessageResponse} indicating if the deletion was successful or not.
     */
    public void deleteAllGamesForCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        List<Game> games = gameRepository.findAllByUsername(username);
        for (Game game : games) {
            gameRepository.delete(game);
        }
    }
}
