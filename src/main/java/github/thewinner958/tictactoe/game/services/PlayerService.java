package github.thewinner958.tictactoe.game.services;


import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.game.services.mappers.PlayerMapper;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import lombok.extern.apachecommons.CommonsLog;
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
@CommonsLog
public class PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    @Autowired
    public PlayerService(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PlayerDto createPlayer(PlayerDto newPlayerDto) {
        Player newPlayer = mapper.toEntity(newPlayerDto);
        newPlayer.setCreateTime(Instant.now());
        return mapper.toDto(repository.save(newPlayer));
    }

    public List<PlayerDto> listPlayers() {
        List<PlayerDto> result = new ArrayList<>();
        for (Player player : repository.findAll()) {
            result.add(mapper.toDto(player));
        }
        return result;
    }

    public Optional<PlayerDto> getPlayerById(int id) {
        if (id == 1) return Optional.empty();
        Optional<Player> player = repository.findById(id);
        return player.map(mapper::toDto);
    }

    public Optional<PlayerDto> getPlayerByUsername(String username) {
        return repository.findByUsername(username).map(mapper::toDto);
    }

    public Optional<PlayerDto> updatePlayer(PlayerDto update) {
        if (repository.updateEmailAndPasswordByUsername(update.email(), update.password(), update.username()) <= 0) {
            return Optional.empty();
        }
        return repository.findById(update.id()).map(mapper::toDto);
    }

    protected PlayerRepository getRepository() {
        return repository;
    }

    protected PlayerMapper getMapper() {
        return mapper;
    }
}
