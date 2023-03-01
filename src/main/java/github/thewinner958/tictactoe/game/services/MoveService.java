package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.entities.Move;
import github.thewinner958.tictactoe.data.repositories.MoveRepository;
import github.thewinner958.tictactoe.game.services.mappers.MoveMapper;
import github.thewinner958.tictactoe.web.DTOs.MoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoveService {
    private final MoveRepository repository;
    private final MoveMapper mapper;
    private final GameService games;
    private final PlayerService players;
    private final GameSetupService setups;

    @Autowired
    public MoveService(MoveRepository repository, MoveMapper mapper, GameService games, PlayerService players, GameSetupService setups) {
        this.repository = repository;
        this.mapper = mapper;
        this.games = games;
        this.players = players;
        this.setups = setups;
    }

    protected MoveRepository getRepository() {
        return repository;
    }

    protected MoveMapper getMapper() {
        return mapper;
    }

    public MoveDto makeMove(MoveDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public List<MoveDto> getAllMovesOfAllGames() {
        List<Move> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public List<MoveDto> getMovesByGameId(int gameId) {
        return repository.findByGame_Id(gameId).stream().map(mapper::toDto).collect(Collectors.toList());
    }


}
