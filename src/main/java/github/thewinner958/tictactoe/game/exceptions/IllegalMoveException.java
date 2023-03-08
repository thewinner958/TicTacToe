package github.thewinner958.tictactoe.game.exceptions;

/**
 * Exception for illegal moves
 * @author TheWinner
 */
public class IllegalMoveException extends Exception{
    public IllegalMoveException(String message) {
        super(message);
    }

    public IllegalMoveException() {
        super();
    }
}
