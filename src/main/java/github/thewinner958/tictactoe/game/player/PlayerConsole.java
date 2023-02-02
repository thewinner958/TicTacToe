package github.thewinner958.tictactoe.game.player;

import github.thewinner958.tictactoe.game.Move;
import github.thewinner958.tictactoe.game.Node;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("consolePlayer")
public record PlayerConsole(String name) implements Player {

    @Override
    public Move getNextMove(Node state) {
        System.out.println("Current state is:");
        System.out.println(state.toString());

        System.out.println("What is your next move?");
        Scanner input = new Scanner(System.in);
        String[] moveS = input.nextLine().split(" ");
        int[] move = new int[2];
        for (int i = 0; i < 2; i++) {
            try {
                move[i] = Integer.parseInt(moveS[i]);
            } catch (NumberFormatException e) {
                System.out.println("Only numbers are allowed!");
                getNextMove(state);
            }
        }

        return new Move(move[0] - 1, move[1] - 1, state.isPlayerX());
    }
}
