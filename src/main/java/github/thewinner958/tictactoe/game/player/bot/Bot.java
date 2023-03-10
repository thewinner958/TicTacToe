package github.thewinner958.tictactoe.game.player.bot;

import github.thewinner958.tictactoe.game.exceptions.IllegalMoveException;
import github.thewinner958.tictactoe.game.Move;
import github.thewinner958.tictactoe.game.Node;
import github.thewinner958.tictactoe.game.Pair;
import github.thewinner958.tictactoe.game.player.PlayerInterface;

import java.util.List;

//WORK IN PROGRESS
public class Bot implements PlayerInterface {
    private final BotDifficulty difficulty;

    public Bot(BotDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String name() {
        return "KAY/O in " + difficulty.toString() + " mode";
    }

    @Override
    public Move getNextMove(Node state) throws IllegalMoveException {
        var bestMove = findBestMove(state, difficulty.getCheckMovesAhead(), Integer.MIN_VALUE, Integer.MAX_VALUE, state.isPlayerX());
        System.out.printf("%s, to get %f score. \n", bestMove.key(), bestMove.value().floatValue());

        return bestMove.key();
    }

    // TODO Our Bot considers that the opponent will play optimally and if they see "inevitable"
    //  defeat - the bot just chooses the first child, basically surrendering.
    //  Fix the Bot to try to prolong the game in hope the opponent will make a mistake
    private static Pair<Move, Number> findBestMove(Node currentState, int depth, int alpha, int beta, boolean isMaxPlayer) throws IllegalMoveException {
        if (currentState.isFinal()) {
            return new Pair<>(currentState.getOriginMove(), currentState.getScore());
        }
        if (depth == 0) {
            return new Pair<>(currentState.getOriginMove(), getEvaluation(currentState));
        }

        List<Node> children = currentState.getChildren();
        Node firstChild = children.get(0);
        Pair<Move, Number> bestMove = new Pair<>(firstChild.getOriginMove(), findBestMove(firstChild, depth - 1, alpha, beta, !isMaxPlayer).value());

        for (int i = 1; i < children.size(); i++) {
            Node child = children.get(i);

            // TODO For a bigger board this gets too slow, fix heuristic and implement alpha-beta pruning
            Pair<Move, Number> bestMoveFromChild = new Pair<>(
                    child.getOriginMove(), findBestMove(child, depth - 1, alpha, beta, !isMaxPlayer).value());
            bestMove = greaterThan(bestMoveFromChild.value(), bestMove.value(), isMaxPlayer) ?
                    bestMoveFromChild : bestMove;
        }
        return bestMove;
    }

    // TODO : Write a better heuristic, but for now - the unknown is scary!
    public static float getEvaluation(Node currentState) {
        return (currentState.isPlayerX() ? -0.5f : 0.5f);
    }

    private static boolean greaterThan(Number firstScore, Number secondScore, boolean isMaxPlayer) {
        double diff = firstScore.floatValue() - secondScore.floatValue();
        if (isMaxPlayer) {
            return diff > 0;
        }
        return diff < 0;
    }
}
