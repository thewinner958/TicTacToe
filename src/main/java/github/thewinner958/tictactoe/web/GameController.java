package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.game.services.GameService;
import github.thewinner958.tictactoe.web.DTOs.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public @ResponseBody GameDto createGame(GameDto dto) {
        return gameService.createGame(dto);
    }

    @GetMapping
    public List<GameDto> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public @ResponseBody GameDto getGameById(@PathVariable Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id!");
        }
        return gameService.getGameById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found"));
    }

    @GetMapping("/{id}/state")
    public @ResponseBody String getGamesCurrentState(@PathVariable Integer id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id!");
        }
        try {
            return gameService.getCurrentState(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nothing was found with that id!");
        }
    }
    // TODO: 06/03/2023 make the move functionality
}
