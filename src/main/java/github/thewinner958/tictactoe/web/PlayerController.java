package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.game.services.PlayerService;
import github.thewinner958.tictactoe.game.services.DTOs.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PlayerDto getPlayerById(@PathVariable Integer id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping
    public PlayerDto registerPlayer(@RequestBody PlayerDto register) {
        return playerService.createPlayer(register);
    }

    @PutMapping
    public PlayerDto updatePlayer(@RequestBody PlayerDto update) {
        return playerService.updatePlayer(update);
    }
}
