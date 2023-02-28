package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.repositories.GameRepository;
import github.thewinner958.tictactoe.data.repositories.GameSetupRepository;
import github.thewinner958.tictactoe.data.repositories.MoveRepository;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.game.services.mappers.GameMapper;
import github.thewinner958.tictactoe.game.services.mappers.GameSetupMapper;
import github.thewinner958.tictactoe.game.services.mappers.MoveMapper;
import github.thewinner958.tictactoe.game.services.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveService {
    private final MoveRepository repository;
    private final GameRepository games;
    private final GameSetupRepository setups;
    private final PlayerRepository players;
    private final MoveMapper mapper;

    @Autowired
    public MoveService(MoveRepository repository, GameRepository games, GameSetupRepository setups, PlayerRepository players, MoveMapper mapper, GameMapper gameMapper, PlayerMapper playerMapper) {
        this.repository = repository;
        this.games = games;
        this.setups = setups;
        this.players = players;
        this.mapper = mapper;
    }

    // TODO: 28/02/2023 Make the service class
}
