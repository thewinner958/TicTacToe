package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.entities.Game;
import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.data.repositories.GameRepository;
import github.thewinner958.tictactoe.data.repositories.GameSetupRepository;
import github.thewinner958.tictactoe.data.repositories.MoveRepository;
import github.thewinner958.tictactoe.game.GameSetUp;
import github.thewinner958.tictactoe.game.Move;
import github.thewinner958.tictactoe.game.Node;
import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import github.thewinner958.tictactoe.game.player.PlayerInterface;
import github.thewinner958.tictactoe.game.player.bot.Bot;
import github.thewinner958.tictactoe.game.player.bot.BotDifficulty;
import github.thewinner958.tictactoe.game.services.mappers.GameMapper;
import github.thewinner958.tictactoe.game.services.mappers.MoveMapper;
import github.thewinner958.tictactoe.web.DTOs.GameDto;
import github.thewinner958.tictactoe.web.DTOs.GameSetupDto;
import github.thewinner958.tictactoe.web.DTOs.MoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final GameRepository repository;
    private final GameMapper mapper;
    private final MoveRepository moveRepository;
    private final MoveMapper moveMapper;
    private final PlayerService players;
    private final GameSetupService setups;

    @Autowired
    public GameService(GameRepository repository, PlayerService players, GameSetupService setups, GameMapper mapper,
                       GameSetupRepository gameSetupRepository, MoveRepository moveRepository, MoveMapper moveMapper, github.thewinner958.tictactoe.game.Game game) {
        this.repository = repository;
        this.players = players;
        this.setups = setups;
        this.mapper = mapper;
        this.moveRepository = moveRepository;
        this.moveMapper = moveMapper;
        this.game = game;
    }

    public GameDto createGame(GameDto dto) {
        if (dto.gameSetup().id() == null) dto = new GameDto(dto.id(), dto.player1(), dto.player2(), setups.getSetup(1).orElseThrow(), Instant.now(), dto.gameStatus(),dto.state(), dto.whoWon());
        else dto = new GameDto(dto.id(), dto.player1(), dto.player2(), dto.gameSetup(), Instant.now(), dto.gameStatus(),dto.state(), dto.whoWon());
        GameSetupDto setupDto = setups.getSetup(dto.gameSetup().id()).orElseThrow();
        char[][] emptyState = new char[Math.toIntExact(setupDto.dimension())][Math.toIntExact(setupDto.dimension())];
        for (int i = 0; i < setupDto.dimension(); i++) {
            for (int j = 0; j < setupDto.dimension(); j++) {
                emptyState[i][j] = setupDto.emptyChar();
            }
        }
        Node emptyNode = new Node(new GameSetUp(setupDto.player1Char(), setupDto.player2Char(), setupDto.emptyChar(), setupDto.dimension(), setupDto.dimension()), emptyState, null);
        game = new github.thewinner958.tictactoe.game.Game(emptyNode);
        game.setPlayer1(new RestPlayer(players.getMapper().toEntity(players.getPlayerByUsername(dto.player1().username()).orElseThrow())));
        if (dto.player2() == null) dto = new GameDto(dto.id(), dto.player1(), players.getPlayerById(1).orElseThrow(), dto.gameSetup(),dto.created(), dto.gameStatus(), game.getState().toString(), null);
        else dto = new GameDto(dto.id(), dto.player1(), dto.player2(), dto.gameSetup(),dto.created(), dto.gameStatus(), game.getState().toString(), null);
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

    public String getCurrentState(int id) {
        return getGameById(id).orElseThrow().state();
    }

    public Optional<GameDto> setSecondPlayer(int id, String username) {
        if (username.equals(players.getPlayerById(1).orElseThrow().username()) || username.equals(players.getPlayerById(2).orElseThrow().username())) return Optional.empty();
        int success = repository.updatePlayer2ByGameId(players.getMapper().toEntity(players.getPlayerByUsername(username).orElseThrow()), id);
        if (success <= 0) return Optional.empty();
        game.setPlayer2(new RestPlayer(players.getMapper().toEntity(players.getPlayerByUsername(username).orElseThrow())));
        return getGameById(id);
    }

    public Optional<GameDto> setBot(int id, int botDifficulty) {
        if (getGameById(id).orElseThrow().player2().id() != 1 && getGameById(id).orElseThrow().player2().id() != 2) return Optional.empty();
        int success = repository.updatePlayer2ByGameId(players.getMapper().toEntity(players.getPlayerById(2).orElseThrow()), id);
        if (success <= 0) return Optional.empty();
        //Enhanced switch. Java 17+
        switch (botDifficulty) {
            case 1 -> game.setPlayer2(new Bot(BotDifficulty.EASY));
            case 2 -> game.setPlayer2(new Bot(BotDifficulty.NORMAL));
            case 4 -> game.setPlayer2(new Bot(BotDifficulty.HARD));
            case 6 -> game.setPlayer2(new Bot(BotDifficulty.EXPERT));
            default -> {
                return Optional.empty();
            }
        }
        return getGameById(id);
    }

    public Optional<GameDto> findWin(int id) {
        int success;
        if (game.getState().getScore() == 0 || game.getState().getScore() == null) {
            success = 1;
        } else if (game.getState().getScore() == 1) {
            success = repository.updateGameStatusAndWhoWonById(true, false, id);
        } else {
            success = repository.updateGameStatusAndWhoWonById(true, true, id);
        }
        if (success <= 0) return Optional.empty();
        return getGameById(id);
    }
    // TODO: 06/03/2023 make the move functionality
    public List<MoveDto> getAllMovesByGameId(int id) {
        return moveRepository.getAllMovesByGameId(id).stream().map(moveMapper::toDto).collect(Collectors.toList());
    }

    public Optional<MoveDto> getLatestMoveByGameId(int id) {
        return Optional.ofNullable(getAllMovesByGameId(id).get(getAllMovesByGameId(id).size() - 1));
    }

    public MoveDto makeMove(MoveDto body) throws IllegalMoveException {
        if (body.player().username().equals(getLatestMoveByGameId(body.game().id()).orElseThrow().player().username())) {
            return null;
        }
        game.move(new Move(body.boardRow().intValue(), body.boardColumn().intValue(), !getLatestMoveByGameId(body.game().id()).orElseThrow().game().player1().username().equals(game.getPlayer1().name())));
        return moveMapper.toDto(moveRepository.save(moveMapper.toEntity(body)));
    }
}
