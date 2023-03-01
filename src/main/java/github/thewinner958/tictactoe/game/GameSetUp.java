package github.thewinner958.tictactoe.game;

import net.jcip.annotations.Immutable;

@Immutable
public class GameSetUp {
    private final char xPlayerSymbol;
    private final char opponentSymbol;
    private final char emptySymbol;
    private final long dimension;
    private final long countToWin;

    public GameSetUp() {
        this(3, 3);
    }

    public GameSetUp(long dimension, long countToWin) {
        this('X', 'O', '_', dimension, countToWin);
    }

    public GameSetUp(char xPlayerSymbol, char opponentSymbol, char emptySymbol, long dimension, long countToWin) {
        if (xPlayerSymbol == 0 || opponentSymbol == 0 || emptySymbol == 0) {
            throw new RuntimeException("Illegal character was used, please use other symbol");
        }
        this.xPlayerSymbol = xPlayerSymbol;
        this.opponentSymbol = opponentSymbol;
        this.emptySymbol = emptySymbol;
        this.dimension = dimension;
        this.countToWin = countToWin;
    }

    public char getXPlayerSymbol() {
        return xPlayerSymbol;
    }

    public char getOpponentSymbol() {
        return opponentSymbol;
    }

    public char getEmptySymbol() {
        return emptySymbol;
    }

    public long getDimension() {
        return dimension;
    }

    public long getCountToWin() {
        return countToWin;
    }
}
