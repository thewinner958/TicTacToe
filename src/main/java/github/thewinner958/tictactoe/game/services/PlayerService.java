package github.thewinner958.tictactoe.game.services;


import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.game.services.mappers.PlayerMapper;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Player createPlayer(PlayerDto newPlayerDto) {
        Player newPlayer = playerMapper.toEntity(newPlayerDto);
        return playerRepository.save(newPlayer);
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

    public PlayerDto getPlayerByUsername(String username) {
        return playerMapper.toDto(playerRepository.findByUsername(username));
    }

    public int updatePlayer(PlayerDto update) {
        return playerRepository.updateEmailAndPasswordByUsername(update.email(), update.password(), update.username());
    }
}
