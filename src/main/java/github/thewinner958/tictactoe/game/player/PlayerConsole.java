package github.thewinner958.tictactoe.game.player;

import github.thewinner958.tictactoe.game.Move;
import github.thewinner958.tictactoe.game.Node;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Scanner;

@Component("consolePlayer")
public final class PlayerConsole implements PlayerInterface {
    private final String name;

    public PlayerConsole(String name) {
        this.name = name;
    }

    private Move playerMove;

    public String getName() {
        return name;
    }

    public Move getPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(Move playerMove) {
        this.playerMove = playerMove;
    }

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

        return new Move(move[0], move[1], state.isPlayerX());
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PlayerConsole) obj;
        return Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PlayerConsole[" +
                "name=" + name + ']';
    }

}
