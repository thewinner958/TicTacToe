package github.thewinner958.tictactoe.game.player;

import github.thewinner958.tictactoe.game.Move;
import github.thewinner958.tictactoe.game.Node;

import java.util.Scanner;

public record PlayerConsole(String name) implements Player {

    @Override
    public Move getNextMove(Node state) {
        System.out.println("Current state is:");
        System.out.println(state.toString());

        System.out.println("What is your next move?");
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        int column = input.nextInt();

        return new Move(row, column, state.isPlayerX());
    }
}
