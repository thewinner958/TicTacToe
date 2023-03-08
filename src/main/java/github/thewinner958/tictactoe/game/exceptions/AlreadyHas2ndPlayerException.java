package github.thewinner958.tictactoe.game.exceptions;

/**
 * An exception for a game already having a second player
 * @author TheWinner
 */
public class AlreadyHas2ndPlayerException extends Exception{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public AlreadyHas2ndPlayerException() {
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AlreadyHas2ndPlayerException(String message) {
        super(message);
    }
}
