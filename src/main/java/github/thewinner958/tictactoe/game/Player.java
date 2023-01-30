package github.thewinner958.tictactoe.game;

import java.util.Objects;

public class Player {
    private final String username;
    private final char symbol;
    private final int wins;
    private final int loses;

    //if the player plays against the computer
    public Player(char opponentSymbol) {
        username = "Computer";
        symbol = opponentSymbol;
        wins = 0;
        loses = 0;
    }

    public Player(String username, char symbol, int wins, int loses) {
        this.username = username;
        this.symbol = symbol;
        this.wins = wins;
        this.loses = loses;
    }

    public String getUsername() {
        return username;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(getUsername(), player.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }
}
