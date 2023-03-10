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
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author TheWinner
 */
@Service
@CommonsLog
@Transactional
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
        char[] string = game.state().toCharArray();
        for (int i = 0; i < game.gameSetup().dimension(); i++) {
            for (int j = 0; j < game.gameSetup().dimension(); j++) {
                state[i][j] = string[(int) (j + (i * game.gameSetup().dimension()))];
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
        if (dto.gameSetup() == null) dto = new GameDto(dto.id(), dto.player1(), dto.player2(), setups.getSetup(1).orElseThrow(), Instant.now(), false,dto.state(), dto.whoWon());
        else dto = new GameDto(dto.id(), dto.player1(), dto.player2(), dto.gameSetup(), Instant.now(), false,dto.state(), dto.whoWon());
        GameSetupDto setupDto = setups.getSetup(dto.gameSetup().id()).orElseThrow();
        StringBuilder stateBuilder = new StringBuilder();
        for (int i = 0; i < setupDto.dimension(); i++) {
            for (int j = 0; j < setupDto.dimension(); j++) {
                stateBuilder.append(setupDto.emptyChar());
            }
        }
        PlayerDto player1 = players.getPlayerByUsername(dto.player1().username()).orElseThrow();
        if (dto.player2() == null) dto = new GameDto(dto.id(), player1, players.getNullPlayer(), setupDto ,dto.created(), dto.gameStatus(), stateBuilder.toString(), null);
        else dto = new GameDto(dto.id(), player1, players.getPlayerByUsername(dto.player2().username()).orElseThrow(), setupDto ,dto.created(), dto.gameStatus(), stateBuilder.toString(), null);
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    /**
     * Lists all games in the db
     * @return list of all games in the db
     */
    public List<GameDto> getAllGames() {
         List<Game> result = new ArrayList<>();
         repository.findAll().forEach(result::add);
         return result.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Gets the info of a game, provided by an identifier in the db
     * @param id the identifier in the db
     * @return the game with the same identifier in {@link Optional} form
     */
    public Optional<GameDto> getGameById(int id) {
        return repository.findById(id).map(mapper::toDto);
    }

    /**
     * Gets the current state of the game.
     * Finds the game with the {@link GameService#getGameById(int id)} method
     * @param id id of the game
     * @return the state of the game
     */
    public String getCurrentState(int id) {
        return getGameById(id).orElseThrow().state();
    }

    /**
     * Sets up the second player in the game
     * @param id id of the game
     * @param username the username of the player
     * @return the updated game
     * @throws AlreadyHas2ndPlayerException if the provided game already has a player
     */
    public Optional<GameDto> setSecondPlayer(int id, String username) throws AlreadyHas2ndPlayerException {
        if (!getGameById(id).orElseThrow().player2().equals(players.getNullPlayer())) throw new AlreadyHas2ndPlayerException(); //Checks if player 2 exists in the game
        if (username.equals(players.getNullPlayer().username())) return Optional.empty(); //Checks if either the username parameter has "null"
        int success = repository.updatePlayer2ByGameId(players.getMapper().toEntity(players.getPlayerByUsername(username).orElseThrow()), id);
        if (success <= 0) return Optional.empty(); //Checks for success
        return getGameById(id);
    }

    /**
     * Finds the winner of the game
     * @param id game id
     * @throws FinishedGameException with message
     */
    public void findWin(int id) throws FinishedGameException {
        GameDto game = getGameById(id).orElseThrow();
        if (game.gameStatus()) throw new FinishedGameException("Already finished!");
        char[][] state = stateToChar(game);
        Character winSymbol = null;
        int countSame = 0;
        int emptyCount = 0;
        long N = game.gameSetup().dimension();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (state[i][j] == game.gameSetup().emptyChar()) emptyCount++;
            }
        }

        //Row win
        for (int i = 0; i < N; i++) {
            char searchSymbol = state[i][0];
            for (int j = 0; j < N; j++) {
                if ((state[i][j] == game.gameSetup().player1Char()) || (state[i][j] == game.gameSetup().player2Char()) && state[i][j] == searchSymbol){
                    countSame++;
                }
                if (countSame == game.gameSetup().dimension()) winSymbol = state[i][j];
            }
            countSame = 0;
        }
        
        //Column win
        for (int i = 0; i < N; i++) {
            char searchSymbol = state[0][i];
            for (int j = 0; j < N; j++) {
                if (state[j][i] == game.gameSetup().player1Char() || state[j][i] == game.gameSetup().player2Char() && state[j][i] == searchSymbol)
                    countSame++;
                if (countSame == game.gameSetup().dimension()) winSymbol = state[j][i];
            }
            countSame = 0;
        }

        //Main Diagonal Win
        for (int i = 0; i < N; i++) {
            char searchSymbol = state[i][0];
            if (state[i][i] == game.gameSetup().player1Char() || state[i][i] == game.gameSetup().player2Char() && state[i][i] == searchSymbol) countSame++;
            if (countSame == game.gameSetup().dimension()) winSymbol = state[i][i];
        }
        countSame = 0;

        //Opposing diagonal win
        for (int i = 0; i < N; i++) {
            char searchSymbol = state[(int) (N - i - 1)][0];
            if (state[(int) (N - i - 1)][i] == game.gameSetup().player1Char() || state[(int) (N - i - 1)][i] == game.gameSetup().player2Char()) countSame++;
            if (countSame == game.gameSetup().dimension()) winSymbol = state[(int) (N - i - 1)][i];
        }

        if (winSymbol == game.gameSetup().player1Char()) {
            repository.updateGameStatusAndWhoWonById(true, 1, game.id());
            throw new FinishedGameException("Player 1 wins");
        } else if(winSymbol == game.gameSetup().player2Char()) {
            repository.updateGameStatusAndWhoWonById(true, 2, game.id());
            throw new FinishedGameException("Player 2 wins");
        }

        if (emptyCount == 0) {
            repository.updateGameStatusAndWhoWonById(true, 0, game.id());
            throw new FinishedGameException("Draw");
        }
    }

    /**
     * Gets all moves by the game identifier
     * @param id game identifier
     * @return the list of all moves that was made in that game
     */
    public List<MoveDto> getAllMovesByGameId(int id) {
        return moveRepository.getAllMovesByGameId(id).stream().map(moveMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Gets the latest move
     * @param id game identifier
     * @return the latest move of that game
     */
    public MoveDto getLatestMoveByGameId(int id) {
        if (getAllMovesByGameId(id).size() == 0) return null;
        return getAllMovesByGameId(id).get(getAllMovesByGameId(id).size() - 1);
    }

    /**
     * Make the move in the game
     * @param moveDto the move dto ({@link MoveDto})
     * @return the move that was made
     * @throws IllegalMoveException if the move provided in the dto is either out of bounds or placed in an already used place
     * @throws FinishedGameException if the game is already finished
     */
    public MoveDto makeMove(MoveDto moveDto) throws IllegalMoveException, FinishedGameException {
        GameDto gameDto = getGameById(moveDto.game().id()).orElseThrow();
        if (gameDto.gameStatus()) throw new FinishedGameException("game is finished");
        if (moveDto.player() == null) throw new IllegalArgumentException("player was not set");
        if (getAllMovesByGameId(moveDto.game().id()).size() == 0 && !moveDto.player().username().equals(gameDto.player1().username())) throw new IllegalMoveException("he is not player 1");
        if (moveDto.boardRow() >= moveDto.game().gameSetup().dimension() || moveDto.boardColumn() >= moveDto.game().gameSetup().dimension() ||
        moveDto.boardRow() < 0 || moveDto.boardColumn() < 0) throw new IllegalMoveException("out of bounds");
        if (getLatestMoveByGameId(moveDto.game().id()) != null && moveDto.player().username().equals(getLatestMoveByGameId(moveDto.game().id()).player().username())) {
            throw new IllegalMoveException("player already played");
        }
        char[][] state = stateToChar(gameDto);
        if (state[Math.toIntExact(moveDto.boardRow())][Math.toIntExact(moveDto.boardColumn())] != gameDto.gameSetup().emptyChar())
            throw new IllegalMoveException("Only put in an empty space");
        if (moveDto.player().username().equals(gameDto.player1().username())) {
            state[Math.toIntExact(moveDto.boardRow())][Math.toIntExact(moveDto.boardColumn())] = gameDto.gameSetup().player1Char();
        } else if (moveDto.player().username().equals(gameDto.player2().username())){
            state[Math.toIntExact(moveDto.boardRow())][Math.toIntExact(moveDto.boardColumn())] = gameDto.gameSetup().player2Char();
        }
        int success = repository.updateGameState(stateFromChar(state), moveDto.game().id());
        if (success <= 0) {
            return null;
        }
        moveDto = new MoveDto(moveDto.id(), gameDto, players.getPlayerByUsername(moveDto.player().username()).orElseThrow(), moveDto.boardRow(), moveDto.boardColumn(), Instant.now());
        return moveMapper.toDto(moveRepository.save(moveMapper.toEntity(moveDto)));
    }
}
