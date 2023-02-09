package github.thewinner958.tictactoe.game.player;

import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import github.thewinner958.tictactoe.game.Move;
import github.thewinner958.tictactoe.game.Node;

public interface Player {
    public String name();
    public Move getNextMove(Node state) throws IllegalMoveException;
}
