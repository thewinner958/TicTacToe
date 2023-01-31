package github.thewinner958.tictactoe.game.player.bot;

public enum BotDifficulty {
    EASY(1),
    NORMAL(2),
    HARD(4),
    EXPERT(6);

    private final int checkMovesAhead;

    BotDifficulty(int checkMovesAhead) {
        this.checkMovesAhead = checkMovesAhead;
    }

    public int getCheckMovesAhead() {
        return checkMovesAhead;
    }

    @Override
    public String toString() {
        return Character.toUpperCase(name().charAt(0)) + name().substring(1).toLowerCase();
    }
}
