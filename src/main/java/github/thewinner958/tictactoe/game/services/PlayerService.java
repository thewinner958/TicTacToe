package github.thewinner958.tictactoe.game.services;


import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.game.services.mappers.PlayerMapper;
import github.thewinner958.tictactoe.game.services.DTOs.PlayerDto;
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

    public PlayerDto getPlayerById(int id) {
        try {
            return playerMapper.toDto(playerRepository.findById(id).orElseThrow());
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private PlayerDto getPlayerByUsername(String username) {
        return playerMapper.toDto(playerRepository.findByUsername(username));
    }

    public PlayerDto updatePlayer(PlayerDto update) {
        if (playerRepository.updateEmailAndPasswordByUsername(update.email(), update.password(), update.username()) <= 0) {
            return null;
        }

        return playerMapper.toDto(playerRepository.findByUsername(update.username()));
    }
}
