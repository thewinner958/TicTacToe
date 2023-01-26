package github.thewinner958.tictactoe.game;

import net.jcip.annotations.Immutable;

@Immutable
public class GameSetUp {

    private final char xPlayerSymbol;
    private final char opponentSymbol;

    private final char emptySymbol;

    private final int dimension;

    private final int countToWin;

    public GameSetUp() {
        this(3, 3);
    }

    public GameSetUp(int dimension, int countToWin) {
        this('X', 'O', '_', dimension, countToWin);
    }

    public GameSetUp(char xPlayerSymbol, char opponentSymbol, char emptySymbol, int dimension, int countToWin) {
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

    public int getDimension() {
        return dimension;
    }

    public int getCountToWin() {
        return countToWin;
    }
}
