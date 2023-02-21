package github.thewinner958.tictactoe.game.services;


import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(PlayerDto newPlayerDto) {
        Player newPlayer = new Player();
        newPlayer.setUsername(newPlayerDto.username());
        newPlayer.setEmail(newPlayerDto.email());
        newPlayer.setPassword(newPlayerDto.password());
        newPlayer.setCreateTime(Instant.now());
        newPlayer.setIsBot((byte) 0);
        return playerRepository.save(newPlayer);
    }

    public List<Player> listPlayers() {
        List<Player> result = new ArrayList<>();
        playerRepository.findAll().forEach(result::add);
        return result;
    }

    public Player getPlayerById(int id) {
        try {
            return playerRepository.findById(id).orElseThrow();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public int updatePlayer(PlayerDto update) {
        return playerRepository.updateEmailAndPasswordByUsername(update.email(), update.password(), update.username());
    }
}
