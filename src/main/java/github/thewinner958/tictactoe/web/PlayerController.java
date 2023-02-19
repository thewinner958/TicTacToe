package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.game.services.PlayerService;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
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

    @GetMapping(path = "/all")
    public List<Player> getAllPlayers() {
        return playerService.listPlayers();
    }

    @GetMapping
    public Player getPlayerById(@RequestParam int id) {
        return playerService.getPlayerById(id);
    }

    @PostMapping(path = "/register")
    public Player registerPlayer(@RequestBody PlayerDto register) {
        return playerService.createPlayer(register);
    }

}
