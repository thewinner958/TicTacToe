package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.entities.GameSetup;
import github.thewinner958.tictactoe.data.repositories.GameSetupRepository;
import github.thewinner958.tictactoe.game.services.mappers.GameSetupMapper;
import github.thewinner958.tictactoe.web.DTOs.GameSetupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameSetupService {
    private final GameSetupRepository repository;
    private final GameSetupMapper mapper;

    @Autowired
    public GameSetupService(GameSetupRepository repository, GameSetupMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public GameSetupDto createSetup(GameSetupDto gameSetup) {
        return mapper.toDto(repository.save(mapper.toEntity(gameSetup)));
    }

    public List<GameSetupDto> getAllSetups() {
        List<GameSetup> result = new ArrayList<>();
        for (GameSetup setup : repository.findAll()) {
            result.add(setup);
        }
        return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<GameSetupDto> getSetup(int id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public Optional<GameSetupDto> updateSetup(GameSetupDto update) {
        if (repository.updateById(update.player1Char(), update.player2Char(), update.emptyChar(), update.boardRows(),
                update.boardColumns(), update.id()) <= 0) return Optional.empty();
        return getSetup(update.id());
    }
}
