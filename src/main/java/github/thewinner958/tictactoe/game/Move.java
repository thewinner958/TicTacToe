package github.thewinner958.tictactoe.game;

import net.jcip.annotations.Immutable;

@Immutable
public record Move(int row, int column, boolean isPlayerX) {

    @Override
    public String toString() {
        return String.format("%s moves to [%d, %d]", isPlayerX ? "PlayerInterface X" : "Opponent", row, column);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Move otherMove))
            return false;

        return (this.row == otherMove.row)
                && (this.column == otherMove.column)
                && (this.isPlayerX == otherMove.isPlayerX);
    }

}

