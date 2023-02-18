package github.thewinner958.tictactoe.data.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "Move")
@Table(name = "move", schema = "tictactoe", indexes = {
        @Index(name = "fk_move_game1_idx", columnList = "game_id"),
        @Index(name = "created_UNIQUE", columnList = "created", unique = true),
        @Index(name = "fk_move_player1_idx", columnList = "player_id")
})
public class Move {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @Column(name = "board_row", nullable = false)
    private Long boardRow;

    @Column(name = "board_column", nullable = false)
    private Long boardColumn;

    @Column(name = "created", nullable = false)
    private Instant created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(Long boardRow) {
        this.boardRow = boardRow;
    }

    public Long getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(Long boardColumn) {
        this.boardColumn = boardColumn;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

}