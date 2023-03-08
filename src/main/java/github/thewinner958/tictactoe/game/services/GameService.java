package github.thewinner958.tictactoe.game.services;

import github.thewinner958.tictactoe.data.entities.Game;
import github.thewinner958.tictactoe.data.repositories.GameRepository;
import github.thewinner958.tictactoe.data.repositories.GameSetupRepository;
import github.thewinner958.tictactoe.data.repositories.MoveRepository;
import github.thewinner958.tictactoe.game.exceptions.AlreadyHas2ndPlayerException;
import github.thewinner958.tictactoe.game.exceptions.FinishedGameException;
import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import github.thewinner958.tictactoe.game.services.mappers.GameMapper;
import github.thewinner958.tictactoe.game.services.mappers.MoveMapper;
import github.thewinner958.tictactoe.web.DTOs.GameDto;
import github.thewinner958.tictactoe.web.DTOs.GameSetupDto;
import github.thewinner958.tictactoe.web.DTOs.MoveDto;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author TheWinner
 */
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
                       GameSetupRepository gameSetupRepository, MoveRepository moveRepository, MoveMapper moveMapper) {
        this.repository = repository;
        this.players = players;
        this.setups = setups;
        this.mapper = mapper;
        this.moveRepository = moveRepository;
        this.moveMapper = moveMapper;
    }

    private char[][] stateToChar(GameDto game) {
        char[][] state = new char[Math.toIntExact(game.gameSetup().dimension())][Math.toIntExact(game.gameSetup().dimension())];
        for (int i = 0; i < game.gameSetup().dimension(); i++) {
            for (int j = 0; j < game.gameSetup().dimension(); j++) {
                state[i][j] = game.state().charAt((int) (j * (i * game.gameSetup().dimension())));
            }
        }
        return state;
    }

    private String stateFromChar(char[][] state) {
        StringBuilder stateBuilder = new StringBuilder();
        for (char[] chars : state) {
            for (int j = 0; j < state.length; j++) {
                stateBuilder.append(chars[j]);
            }
            stateBuilder.append("\n");
        }
        return stateBuilder.toString();
    }

    @FunctionalInterface
    interface WinnableSequence {
        char charAt(int start, int index);
    }

    /**
     * Creates a game in the db
     * @param dto {@link GameDto}
     * @return the created game
     */
    public GameDto createGame(GameDto dto) {
        if (dto.gameSetup().id() == null) dto = new GameDto(dto.id(), dto.player1(), dto.player2(), setups.getSetup(1).orElseThrow(), Instant.now(), dto.gameStatus(),dto.state(), dto.whoWon());
        else dto = new GameDto(dto.id(), dto.player1(), dto.player2(), dto.gameSetup(), Instant.now(), dto.gameStatus(),dto.state(), dto.whoWon());
        GameSetupDto setupDto = setups.getSetup(dto.gameSetup().id()).orElseThrow();
        StringBuilder stateBuilder = new StringBuilder();
        for (int i = 0; i < setupDto.dimension(); i++) {
            for (int j = 0; j < setupDto.dimension(); j++) {
                stateBuilder.append(setupDto.emptyChar());
            }
            stateBuilder.append("\n");
        }
        PlayerDto player1 = players.getPlayerByUsername(dto.player1().username()).orElseThrow();
        if (dto.player2() == null) dto = new GameDto(dto.id(), player1, players.getPlayerById(1).orElseThrow(), setupDto ,dto.created(), dto.gameStatus(), stateBuilder.toString(), null);
        else dto = new GameDto(dto.id(), player1, players.getPlayerByUsername(dto.player2().username()).orElseThrow(), setupDto ,dto.created(), dto.gameStatus(), stateBuilder.toString(), null);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    /**
     * A function that lists all games in the db
     * @return list of all games in the db
     */
    public List<GameDto> getAllGames() {
         List<Game> result = new ArrayList<>();
         repository.findAll().forEach(result::add);
         return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public Optional<GameDto> getGameById(int id) {
        findWin(id);
        return repository.findById(id).map(mapper::toDto);
    }

    public String getCurrentState(int id) {
        return getGameById(id).orElseThrow().state();
    }

    public Optional<GameDto> setSecondPlayer(int id, String username) throws AlreadyHas2ndPlayerException {
        if (!getGameById(id).orElseThrow().player2().equals(players.getPlayerById(1).orElseThrow())) throw new AlreadyHas2ndPlayerException(); //Checks if player 2 exists in the game
        if (username.equals(players.getPlayerById(1).orElseThrow().username())) return Optional.empty(); //Checks if either the username parameter has "null"
        int success = repository.updatePlayer2ByGameId(players.getMapper().toEntity(players.getPlayerByUsername(username).orElseThrow()), id);
        if (success <= 0) return Optional.empty(); //Checks for success
        return getGameById(id);
    }

    protected Character findWin(int id) {
        GameDto game = getGameById(id).orElseThrow();
        if (game.gameStatus()) return '1';
        char[][] state = stateToChar(game);
        char winSymbol;
        int countSame;
        long N = game.gameSetup().dimension();
        WinnableSequence[] ws = {
                (int i, int j) -> { // Row sequence
                    return state[i][j];
                },

                (int i, int j) -> { // Column sequence
                    return state[j][i];
                },

                (int i, int j) -> { // Main diagonal - up
                    return state[i + j][j];
                },

                (int i, int j) -> {  // Opposing diagonal - down
                    return state[j][i + j];
                },

                (int i, int j) -> { // Main diagonal - up
                    return state[i - j][j];
                },

                (int i, int j) -> {// Opposing diagonal - down
                    return state[(int) (N - j - 1)][i + j];
                }
        };

        for (WinnableSequence seq : ws) {
            for (int i = 0; i < N; i++) {
                winSymbol = seq.charAt(i, 0);
                countSame = 0;
                for (int j = 0; j < N; j++) {
                    try {
                        if (seq.charAt(i, j) != game.gameSetup().emptyChar() &&
                                seq.charAt(i, j) == winSymbol) {
                            countSame = countSame + 1;
                        } else {
                            winSymbol = seq.charAt(i, j);
                            countSame = 1;
                        }

                        if (countSame == game.gameSetup().dimension()) {
                            int success;
                            if (winSymbol == game.gameSetup().player1Char()) {
                                success = repository.updateGameStatusAndWhoWonById(true,1, id); //Player 1
                            } else {
                                success = repository.updateGameStatusAndWhoWonById(true,2, id); //Player 2
                            }
                            if (success > 0) return winSymbol;
                            else return null;
                        }

                    } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                        break;
                    }
                }
            }
        }
        boolean emptyChar = true;
        for (int i = 0; i < game.state().length(); i++) {
            emptyChar = game.state().charAt(i) == game.gameSetup().emptyChar();
            if (emptyChar) break;
        }
        if (!emptyChar) {
            repository.updateGameStatusAndWhoWonById(true, 0, id);
            return game.gameSetup().emptyChar(); //Draw
        }
        return null;
    }

    // TODO: 06/03/2023 make the move functionality
    public List<MoveDto> getAllMovesByGameId(int id) {
        return moveRepository.getAllMovesByGameId(id).stream().map(moveMapper::toDto).collect(Collectors.toList());
    }

    public MoveDto getLatestMoveByGameId(int id) {
        return getAllMovesByGameId(id).get(getAllMovesByGameId(id).size() - 1);
    }

    public MoveDto makeMove(MoveDto moveDto) throws IllegalMoveException, FinishedGameException {
        Character winSymbol = findWin(moveDto.game().id());
        GameDto gameDto = getGameById(moveDto.game().id()).orElseThrow();
        if (winSymbol != null || gameDto.gameStatus()) throw new FinishedGameException();
        if (moveDto.player() == null) throw new IllegalArgumentException();
        if (moveDto.boardRow() >= moveDto.game().gameSetup().dimension() || moveDto.boardColumn() >= moveDto.game().gameSetup().dimension() ||
        moveDto.boardRow() < 0 || moveDto.boardColumn() < 0) throw new IllegalMoveException();
        if (moveDto.player().username().equals(getLatestMoveByGameId(moveDto.game().id()).player().username())) {
            throw new IllegalArgumentException();
        }
        char[][] state = stateToChar(gameDto);
        if (state[Math.toIntExact(moveDto.boardRow())][Math.toIntExact(moveDto.boardColumn())] != gameDto.gameSetup().emptyChar())
            throw new IllegalMoveException();
        if (moveDto.player().username().equals(gameDto.player1().username())) {
            state[Math.toIntExact(moveDto.boardRow())][Math.toIntExact(moveDto.boardColumn())] = gameDto.gameSetup().player1Char();
        } else {
            state[Math.toIntExact(moveDto.boardRow())][Math.toIntExact(moveDto.boardColumn())] = gameDto.gameSetup().player2Char();
        }
        int success = repository.updateGameState(stateFromChar(state), moveDto.game().id());
        if (success <= 0) {
            return null;
        }
        return moveMapper.toDto(moveRepository.save(moveMapper.toEntity(moveDto)));
    }
}
