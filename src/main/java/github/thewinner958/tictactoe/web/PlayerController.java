package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.game.services.PlayerService;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("players")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerDto> getAllPlayers() {
        return playerService.listPlayers();
    }

    @GetMapping("/{id}")
    public @ResponseBody PlayerDto getPlayerById(@PathVariable Integer id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id.");
        Optional<PlayerDto> result = playerService.getPlayerById(id);
        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
    }

    @PostMapping
    public PlayerDto registerPlayer(@RequestBody PlayerDto register) {
        return playerService.createPlayer(register);
    }

    @PutMapping
    public @ResponseBody PlayerDto updatePlayer(@RequestBody PlayerDto update) {
        PlayerDto result = playerService.updatePlayer(update);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player does not exit");
        }
        return playerService.updatePlayer(update);
    }
}
