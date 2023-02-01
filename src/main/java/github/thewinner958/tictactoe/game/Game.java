package github.thewinner958.tictactoe.game;

import github.thewinner958.tictactoe.game.player.Player;

import java.util.Optional;


public class Game {
    private Node state;
    private Player player1;
    private Player player2;
    private int finalScore;

    public Optional<Integer> getFinalScore() {
        return Optional.of(finalScore);
    }

    public Game() {
        this(new GameSetUp());
    }

    public Game(Node startNode) {
        this.state = startNode;
    }

    public Game(GameSetUp setUp) {
        this(Node.empty(setUp));
    }

    public Game(char[][] state) {
        this(new Node(new GameSetUp(state.length, state.length), state, null));
    }

    public Game(GameSetUp setUp, char[][] state) {
        this(new Node(setUp, state, null));
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Play the game between two competing players - changing turn and each one making their move.
     *
     * @param moves How many moves should be played. Pass a negative number to play the game to the end.
     */
    public void play(int moves) {
        if (player1 == null || player2 == null) {
            System.out.println("Both player should join before the game begins");
            return;
        }
        // Player 1 always starts first.
        Player whoseTurn = player1;
        int movesLeft = moves;

        while (!state.isFinal() && movesLeft != 0) {
            Move move = whoseTurn.getNextMove(this.state);
            this.move(move);
            System.out.printf("%s made a move: %s \n", whoseTurn.name(), move.toString());

            whoseTurn = (whoseTurn == player1) ? player2 : player1;
            movesLeft--;
        }

        if (state.isFinal()) {
            finalScore = state.getScore();

            System.out.print("Game Over!\n");
            System.out.println(state.toString());
            if (finalScore == 0) {
                System.out.println("It's a draw!");
            } else {
                String wonLost = (finalScore > 0) ? "won" : "lost";
                System.out.printf("Player 1: %s %s and gets %d points!\n", player1.name(), wonLost, state.getScore());
            }
        }

    }

    public void move(Move move) {
        this.state = this.state.move(move);
    }

    public Node getState() {
        return this.state;
    }
}

