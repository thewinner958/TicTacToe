package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.game.exceptions.AlreadyHas2ndPlayerException;
import github.thewinner958.tictactoe.game.exceptions.FinishedGameException;
import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import github.thewinner958.tictactoe.game.services.GameService;
import github.thewinner958.tictactoe.web.DTOs.GameDto;
import github.thewinner958.tictactoe.web.DTOs.MoveDto;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CommonsLog
@RequestMapping("games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public @ResponseBody GameDto createGame(@RequestBody GameDto dto) {
        return gameService.createGame(dto);
}

    @PostMapping("/{id}")
    public @ResponseBody GameDto setPlayer2(@PathVariable Integer id, @RequestParam(name = "player2") String username) {
        try {
            return gameService.setSecondPlayer(id, username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The username provided is not valid or the game already has a player"));
        } catch (AlreadyHas2ndPlayerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The game already has a second player");
        }
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

    @GetMapping("/{id}/win")
    public @ResponseBody void findWin(@PathVariable Integer id) {
        try {
            gameService.findWin(id);
        } catch (FinishedGameException e) {
            throw new ResponseStatusException(HttpStatus.OK, e.getMessage());
        }
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

    @PostMapping("/{id}/moves")
    public MoveDto makeMove(@PathVariable Integer id, @RequestBody MoveDto body) {
        if (body.game() == null) {
            GameDto game = getGameById(id);
            body = new MoveDto(body.id(), game, body.player(), body.boardRow(), body.boardColumn(), body.created());
        }
        try {
            return gameService.makeMove(body);
        }catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IllegalMoveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (FinishedGameException e) {
            throw new ResponseStatusException(HttpStatus.ACCEPTED, e.getMessage());
        }
    }

    @GetMapping("/{id}/moves")
    public List<MoveDto> getGamesMoves(@PathVariable Integer id) {
        return gameService.getAllMovesByGameId(id);
    }

    @GetMapping("/{id}/move")
    public MoveDto getLatestGameMove(@PathVariable Integer id) {
        return gameService.getLatestMoveByGameId(id);
    }
}
