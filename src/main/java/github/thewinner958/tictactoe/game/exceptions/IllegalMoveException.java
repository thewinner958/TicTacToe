package github.thewinner958.tictactoe.game.exceptions;

public class IllegalMoveException extends Exception{
    public IllegalMoveException(String message) {
        super(message);
    }

    public IllegalMoveException() {
        super();
    }
}
