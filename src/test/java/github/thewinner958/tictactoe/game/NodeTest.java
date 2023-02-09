package github.thewinner958.tictactoe.game;

import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class NodeTest {
    public char[][] startState;

    public Node startNode;

    @BeforeEach
    public void setUp() {
        startState = new char[][]{
                {'X', '_', 'X', 'O'},
                {'O', 'O', 'X', 'X'},
                {'X', 'O', '_', 'O'},
                {'O', 'X', '_', '_'}};
        startNode = new Node(new GameSetUp(4, 3), this.startState, null);
    }

    // FIXME: 08/02/2023 Fix this test
    @Test
    public void testWinRowColumn() throws IllegalMoveException {
        char Null = 0;
        //assertSame('O', startNode.findWin());

        assertSame(Null, startNode.findWin());

        Node node = startNode.move(new Move(4, 3, true));

        assertSame(Null, node.findWin());

        node = node.move(new Move(2, 1, true));
        assertSame('X', node.findWin());

        node = node.move(new Move(1, 1, false));
        assertSame(Null, node.findWin());

        node = node.move(new Move(1, 2, false));
        System.out.println(node);
        assertSame('O', node.findWin());

        node = node.move(new Move(2, 2, true));
        assertSame('X', node.findWin());
    }

    @Test
    public void testPositions() {
        List<Move> moves = startNode.possibleMoves();
        assertSame(4, moves.size());

        assertThat(moves, contains(new Move(1, 2, true),
                new Move(3, 3, true),
                new Move(4, 3, true),
                new Move(4, 4, true)));
    }

    @Test
    public void testToString() {
        assertEquals(getStateString(startState), startNode.toString());
    }

    private static String getStateString(char[][] state) {
        return Arrays.stream(state).map(String::new).collect(Collectors.joining("\n"));
    }


    // Tests immutability
    @Test
    public void testMoveImmutability() throws IllegalMoveException {
        Move move = new Move(1, 2, true);

        String origin = startNode.toString();
        Node result = startNode.move(move);

        assertEquals(origin, startNode.toString(), "Check that the original state has not changed");

        char symbol = result.toString().charAt(
                (startNode.getSetUp().getDimension() * move.row()) + move.column());

        assertSame(move.isPlayerX() ? startNode.getSetUp().getXPlayerSymbol() : startNode.getSetUp().getOpponentSymbol(), symbol);
    }

    @Test
    public void testChildren() throws IllegalMoveException {

        Node node = startNode.move(new Move(1, 2, true));

        List<Move> moves = node.possibleMoves();
        assertEquals(3, moves.size());

        assertThat(moves, contains(
                new Move(3, 3, false),
                new Move(4, 3, false),
                new Move(4, 4, false)));
    }
}
