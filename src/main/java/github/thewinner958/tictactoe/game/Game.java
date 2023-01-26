package github.thewinner958.tictactoe.game;

import java.util.List;


public class Game {


    private Node state;


    public Game(char[][] state) {

        this(new GameSetUp(state.length, state.length), state, null);

    }


    public Game(GameSetUp setUp, char[][] state, Move lastMove) {

        this.state = new Node(setUp, state, lastMove);

    }


    public Pair<Move, Integer> findBestMove() {

        var bestMove = findBestMove(state, state.isPlayerX());

        System.out.printf("%s, to get %d score. \n", bestMove.key(), bestMove.value());


        return bestMove;

    }


    private static Pair<Move, Integer> findBestMove(Node currentState, boolean isMaxPlayer) {

        if (currentState.isFinal()) {

            return new Pair<>(currentState.getOriginMove(), currentState.getScore());

        }

        List<Node> children = currentState.getChildren();

        Node firstChild = children.get(0);

        Pair<Move, Integer> bestMove = new Pair<>(firstChild.getOriginMove(), findBestMove(firstChild, !isMaxPlayer).value());


        for (int i = 1; i < children.size(); i++) {

            Node child = children.get(i);


            Pair<Move, Integer> bestMoveFromChild = new Pair<>(

                    child.getOriginMove(), findBestMove(child, !isMaxPlayer).value());

            bestMove = greaterThan(bestMoveFromChild.value(), bestMove.value(), isMaxPlayer) ?

                    bestMoveFromChild : bestMove;

        }

        return bestMove;

    }


    private static boolean greaterThan(int firstScore, int secondScore, boolean isMaxPlayer) {

        if (isMaxPlayer) {

            return firstScore > secondScore;

        }

        return firstScore < secondScore;

    }


    public void move(Move move) {

        this.state = this.state.move(move);

    }


    public Node getState() {

        return this.state;

    }

}

