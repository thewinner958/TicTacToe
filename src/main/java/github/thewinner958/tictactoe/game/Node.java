package github.thewinner958.tictactoe.game;

import net.jcip.annotations.Immutable;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Immutable
public class Node {
    private final GameSetUp setUp;
    // If you add a getter, make sure you don't allow changes on the state
    private final char[][] state;
    private final Move originMove;
    private boolean isEvaluated = false;
    private Integer score = null;
    private List<Node> children = null;

    private boolean moveValidator(Move move) {
        return move.row() < 0 || move.row() >= setUp.getDimension() || move.column() < 0 || move.column() >= setUp.getDimension();
    }

    public Node(GameSetUp setUp, char[][] state, Move originMove) {
        this.setUp = setUp;
        //Validates the dimensions of the state
        if (setUp.getDimension() != state.length || setUp.getDimension() != state[0].length) {
            throw new RuntimeException("Invalid dimensions of the state array");
        }
        //Validates the characters in the state array from the setUp variable
        boolean contains = false;
        for (char[] chars : state) {
            for (char sym : chars) {
                contains = sym == this.setUp.getXPlayerSymbol() || sym == this.setUp.getOpponentSymbol() || sym == this.setUp.getEmptySymbol();
            }
        }
        if (!contains) {
            throw new RuntimeException("There is an invalid character that was used in the board. The only characters that are allowed are '" + this.setUp.getXPlayerSymbol() + "', '" + this.setUp.getOpponentSymbol() + "' and '" + this.setUp.getEmptySymbol() + "'.");
        }
        this.state = copyState(state);
        if (originMove != null && moveValidator(originMove)) {
            throw new RuntimeException("Invalid move.");
        }
        this.originMove = originMove;
    }

    public Node(Node parentNode, Move move) {
        this.setUp = parentNode.setUp;
        this.state = copyState(parentNode.state);
        char symbol = move.isPlayerX() ? setUp.getXPlayerSymbol() : setUp.getOpponentSymbol();
        try {
            this.state[move.row()][move.column()] = symbol;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid move!");
        }
        finally {
            this.originMove = move;
        }
    }

    private static char[][] copyState(char[][] source) {
        int numberOfColumns = source.length;
        int numberOfRows = source[0].length;
        char[][] destination = new char[numberOfColumns][numberOfRows];
        for (int column = 0; column < numberOfColumns; column++) {
            System.arraycopy(source[column], 0, destination[column], 0, numberOfRows);
        }
        return destination;
    }

    public static Node empty(GameSetUp gameSetUp) {
        int n = gameSetUp.getDimension();
        char[][] emptyState = new char[n][n];

        for (char[] row : emptyState) {
            Arrays.fill(row, gameSetUp.getEmptySymbol());
        }

        return new Node(gameSetUp, emptyState, null);
    }

    public Node move(Move move) {
        return new Node(this, move);
    }

    public GameSetUp getSetUp() {
        return setUp;
    }

    public boolean isPlayerX() {
        if (originMove != null) {
            return !originMove.isPlayerX();
        }
        return true; // X's are first
    }

    public Move getOriginMove() {
        return originMove;
    }

    public List<Node> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
            List<Move> possibleMoves = possibleMoves();
            for (Move move : possibleMoves) {
                this.children.add(this.move(move));
            }
        }
        return this.children;
    }

    protected List<Move> possibleMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < setUp.getDimension(); i++) {
            for (int j = 0; j < setUp.getDimension(); j++) {
                if (state[i][j] == setUp.getEmptySymbol()) {
                    moves.add(new Move(i, j, isPlayerX()));
                }
            }
        }
        return moves;
    }

    // Calling this method would evaluate the node if it was not done already
    public boolean isFinal() {
        return (getScore() != null);
    }

    // Calling this method would evaluate the node if it was not done already
    public Integer getScore() {
        if (!isEvaluated) {
            char winSymbol = findWin();
            if (winSymbol != 0) {
                this.score = (winSymbol == setUp.getXPlayerSymbol()) ? 1 : -1;
            } else if (possibleMoves().isEmpty()) {
                this.score = 0;
            } else {
                this.score = null;
            }
            this.isEvaluated = true;
        }
        return this.score;
    }

    protected char findWin() {
        char winSymbol;
        int countSame;
        int N = setUp.getDimension();
        WinnableSequence[] ws = {
                (int i, int j) -> { // Row sequence
                    return this.state[i][j];
                },

                (int i, int j) -> { // Column sequence
                    return this.state[j][i];
                },

                (int i, int j) -> { // Main diagonal - up
                    return this.state[i + j][j];
                },

                (int i, int j) -> {  // Opposing diagonal - down
                    return this.state[j][i + j];
                },

                (int i, int j) -> { // Main diagonal - up
                    return this.state[i - j][j];
                },

                (int i, int j) -> {// Opposing diagonal - down
                    return this.state[N - j - 1][i + j];
                }
        };

        for (WinnableSequence seq : ws) {
            for (int i = 0; i < N; i++) {
                winSymbol = seq.charAt(i, 0);
                countSame = 0;
                for (int j = 0; j < N; j++) {
                    try {
                        if (seq.charAt(i, j) != setUp.getEmptySymbol() &&
                                seq.charAt(i, j) == winSymbol) {
                            countSame = countSame + 1;
                        } else {
                            winSymbol = seq.charAt(i, j);
                            countSame = 1;
                        }

                        if (countSame == setUp.getCountToWin()) {
                            return winSymbol;
                        }

                    } catch (java.lang.ArrayIndexOutOfBoundsException exc) {
                        break;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < setUp.getDimension(); row++) {
            for (int column = 0; column < setUp.getDimension(); column++) {
                builder.append(this.state[row][column]);
            }
            if (row < setUp.getDimension() - 1) builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Node otherNode = (Node) other;
        return (this.isPlayerX() == otherNode.isPlayerX()) && Arrays.deepEquals(this.state, otherNode.state);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(isPlayerX())
                + Arrays.deepHashCode(state);
    }

    /**
     * A functional interface to define possible traversal sequence
     * for the elements of the state matrix.
     * The state would indicate a winning state when there is a sequence of same symbols,
     * longer that the {@link GameSetUp#getCountToWin}
     */
    @FunctionalInterface
    interface WinnableSequence {
        char charAt(int start, int index);
    }

}