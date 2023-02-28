package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.repositories.GameRepository;
import github.thewinner958.tictactoe.data.repositories.GameSetupRepository;
import github.thewinner958.tictactoe.data.repositories.PlayerRepository;
import github.thewinner958.tictactoe.game.Game;
import github.thewinner958.tictactoe.game.services.mappers.GameMapper;
import github.thewinner958.tictactoe.game.services.mappers.GameSetupMapper;
import github.thewinner958.tictactoe.game.services.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameRepository repository;
    private final PlayerRepository players;
    private final GameSetupRepository setups;
    private final GameMapper mapper;
    private final Game game;

    @Autowired
    public GameService(GameRepository repository, PlayerRepository players, GameSetupRepository setups, GameMapper mapper, PlayerMapper playerMapper, GameSetupMapper setupMapper, Game game) {
        this.repository = repository;
        this.players = players;
        this.setups = setups;
        this.mapper = mapper;
        this.game = game;
    }

    // TODO: 28/02/2023 Make the service class
}
