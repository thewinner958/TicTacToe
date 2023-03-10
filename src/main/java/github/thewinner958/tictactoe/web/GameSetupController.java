package github.thewinner958.tictactoe.web;

import github.thewinner958.tictactoe.game.services.GameSetupService;
import github.thewinner958.tictactoe.web.DTOs.GameSetupDto;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CommonsLog
@RequestMapping("setups")
public class GameSetupController {
    private final GameSetupService service;

    @Autowired
    public GameSetupController(GameSetupService service) {
        this.service = service;
    }

    @GetMapping
    public List<GameSetupDto> getAll() {
        return service.getAllSetups();
    }

    @GetMapping("/{id}")
    public @ResponseBody GameSetupDto getSetupById (@PathVariable Integer id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id!");
        return service.getSetup(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setup was not found!"));
    }

    @PostMapping
    public @ResponseBody GameSetupDto createSetup(@RequestBody GameSetupDto setup) {
        return service.createSetup(setup);
    }

    @PutMapping
    public @ResponseBody GameSetupDto updateSetup(@RequestBody GameSetupDto update) {
        if (update.id() == 1) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Can't update this!");
        return service.updateSetup(update).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find id!"));
    }
}
