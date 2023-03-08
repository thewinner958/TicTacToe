package github.thewinner958.tictactoe.game.exceptions;

/**
 * Exception for the finished games
 * @author TheWinner
 */
public class FinishedGameException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public FinishedGameException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FinishedGameException(String message) {
        super(message);
    }
}
