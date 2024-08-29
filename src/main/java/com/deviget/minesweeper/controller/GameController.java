package com.deviget.minesweeper.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.deviget.minesweeper.game.service.GameService;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.payload.dto.GameDto;
import com.deviget.minesweeper.payload.response.MessageResponse;

/**
 * Controller class which handles the life cycle of mine sweeper games.
 *
 * @author david.rios
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/game")
@PreAuthorize("hasRole('USER')")
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     * Interacts with {@link GameService} to create a new game associated to the current user.
     *
     * @param request - a {@link GameDto} instance with the game details.
     * @return the location of created game as a Response Header.
     */
    @PostMapping()
    public ResponseEntity<?> saveGame(@Valid @RequestBody GameDto gameDto) {
        Game game = gameService.saveGame(gameDto);
        gameDto = GameDto.fromGame(game, true);
        return ResponseEntity.ok(gameDto);
    }

    /**
     * Interacts with {@link GameService} to get all games for current user.
     *
     * @return a {@link List} of {@link Game} instances associated to the authenticated user.
     */
    @GetMapping()
    public ResponseEntity<?> getAllGamesForCurrentUser() {
        List<GameDto> games = gameService.getAllGamesForCurrentUser()
                .stream()
                .map(game -> GameDto.fromGame(game, false))
                .collect(Collectors.toList());
        return ResponseEntity.ok(games);
    }

    /**
     * Interacts with {@link GameService} to get an individual game.
     *
     * @param id - the game id.
     * @return a {@link Game} instance if the game is associated to the authenticated user, an error otherwise.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGame(@PathVariable Long id) {
        Game game = gameService.getGame(id);
        GameDto gameDto = GameDto.fromGame(game, true);
        return ResponseEntity.ok(gameDto);
    }

    /**
     * Interacts with {@link GameService} to delete an individual game associated to the current user.
     *
     * @param id - the game id.
     * @return a {@link MessageResponse} indicating if the deletion was successful or not.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok(new MessageResponse("Game deleted"));
    }

    /**
     * Interacts with {@link GameService} to delete all games associated to the current user.
     *
     * @return a {@link MessageResponse} indicating if the deletion was successful or not.
     */
    @DeleteMapping()
    public ResponseEntity<?> deleteAllGamesForCurrentUser() {
        gameService.deleteAllGamesForCurrentUser();
        return ResponseEntity.ok(new MessageResponse("All Games deleted"));
    }
}
