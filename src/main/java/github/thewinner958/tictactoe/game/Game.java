package github.thewinner958.tictactoe.game;

import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import github.thewinner958.tictactoe.game.player.Player;
import io.micrometer.core.instrument.config.validate.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Game {
    private Node state;
    private Player player1;
    private Player player2;
    private int finalScore;

    public Optional<Integer> getFinalScore() {
        return Optional.of(finalScore);
    }

    public Game() {
        this(new GameSetUp());
    }

    public Game(Node startNode) {
        this.state = startNode;
    }

    public Game(GameSetUp setUp) {
        this(Node.empty(setUp));
    }

    public Game(char[][] state) {
        this(new Node(new GameSetUp(state.length, state.length), state, null));
    }

    @Autowired
    public Game(GameSetUp setUp, char[][] state) {
        this(new Node(setUp, state, null));
    }

    @Autowired
    @Qualifier("consolePlayer")
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    @Autowired
    @Qualifier("bot")
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Play the game between two competing players - changing turn and each one making their move.
     *
     * @param moves How many moves should be played. Pass a negative number to play the game to the end.
     */
    public void play(int moves) throws IllegalMoveException {
        if (player1 == null || player2 == null) {
            System.out.println("Both player should join before the game begins");
            return;
        }
        // Player 1 always starts first.
        Player whoseTurn = state.isPlayerX() ? player1 : player2;
        int movesLeft = moves;

        while (!state.isFinal() && movesLeft != 0) {
            Move move = whoseTurn.getNextMove(this.state);
            try {
                this.move(move);
            } catch (IllegalMoveException e) {
                System.out.println("Invalid move! Trying to place it on a used space");
                continue;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid move! Out of bounds");
                continue;
            }
            System.out.printf("%s made a move: %s \n", whoseTurn.name(), move.toString());
            whoseTurn = (whoseTurn == player1) ? player2 : player1;
            movesLeft--;
        }

        if (state.isFinal()) {
            finalScore = state.getScore();

            System.out.print("Game Over!\n");
            System.out.println(state.toString());
            if (finalScore == 0) {
                System.out.println("It's a draw!");
            } else {
                String wonLost = (finalScore > 0) ? "won" : "lost";
                System.out.printf("Player 1: %s %s and gets %d points!\n", player1.name(), wonLost, state.getScore());
            }
        }

    }

    public void move(Move move) throws IllegalMoveException {
        if (this.state.move(move) == null) {
            throw new IllegalMoveException("You can't place it on already used space!");
        }
        this.state = this.state.move(move);
    }

    public Node getState() {
        return this.state;
    }
}

