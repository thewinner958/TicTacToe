package github.thewinner958.tictactoe.game.services;


import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import github.thewinner958.tictactoe.game.services.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service class for player management
 * @author TheWinner
 */
@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    public PlayerDto createPlayer(PlayerDto newPlayerDto) {
        Player newPlayer = playerMapper.toEntity(newPlayerDto);

        newPlayer.setCreateTime(Instant.now());
        newPlayer.setIsBot((byte) 0);

        return playerMapper.toDto(playerRepository.save(newPlayer));
    }

    public List<PlayerDto> listPlayers() {
        List<PlayerDto> result = new ArrayList<>();
        for (Player player : playerRepository.findAll()) {
            result.add(playerMapper.toDto(player));
        }
        return result;
    }

    public Optional<PlayerDto> getPlayerById(int id) {
        Optional<Player> player = playerRepository.findById(id);
        return player.map(playerMapper::toDto);
    }

    public PlayerDto updatePlayer(PlayerDto update) {
        if (playerRepository.updateEmailAndPasswordByUsername(update.email(), update.password(), update.username()) <= 0) {
            return null;
        }

        return playerMapper.toDto(playerRepository.findByUsername(update.username()));
    }
}
