package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.entities.Game;
import github.thewinner958.tictactoe.data.repositories.GameRepository;
import github.thewinner958.tictactoe.game.services.mappers.GameMapper;
import github.thewinner958.tictactoe.web.DTOs.GameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository repository;
    private final PlayerService players;
    private final GameSetupService setups;
    private final GameMapper mapper;
    /*private final PlayerMapper playerMapper;
    private Game game;

    private static class RestPlayer implements PlayerInterface {
        private final Player player;

        private RestPlayer(Player player) {
            this.player = player;
        }

        @Override
        public String name() {
            return player.getUsername();
        }

        @Deprecated
        @Override
        public Move getNextMove(Node state) throws IllegalMoveException {
            return null;
        }
    }*/

    @Autowired
    public GameService(GameRepository repository, PlayerService players, GameSetupService setups, GameMapper mapper /*,PlayerMapper playerMapper, Game game*/) {
        this.repository = repository;
        this.players = players;
        this.setups = setups;
        this.mapper = mapper;
        /*this.playerMapper = playerMapper;
        this.game = game;*/
    }

    public GameDto createGame(GameDto dto) {
        players.getPlayerById(dto.player1().id()).orElseThrow();
        players.getPlayerById(dto.player1().id()).orElseThrow();
        setups.getSetup(dto.gameSetup().id()).orElseThrow();
        // TODO: 01/03/2023 need to create a game instance for the logic itself
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public List<GameDto> getAllGames() {
         List<Game> result = new ArrayList<>();
         repository.findAll().forEach(result::add);
         return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<GameDto> getGameById(int id) {
        return repository.findById(id).map(mapper::toDto);
    }

    protected GameRepository getRepository() {
        return repository;
    }

    protected GameMapper getMapper() {
        return mapper;
    }
}
