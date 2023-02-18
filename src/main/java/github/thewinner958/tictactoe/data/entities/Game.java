package github.thewinner958.tictactoe.data.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "Game")
@Table(name = "game", schema = "tictactoe", indexes = {
        @Index(name = "fk_player1_idx", columnList = "player1"),
        @Index(name = "fk_player2_idx", columnList = "player2"),
        @Index(name = "fk_game_game_setup1_idx", columnList = "game_setup_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "player1_UNIQUE", columnNames = {"player1"})
})
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player1", nullable = false)
    private Player player1;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "player2", nullable = false)
    private Player player2;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "game_setup_id", nullable = false)
    private GameSetup gameSetup;

    @Column(name = "created", nullable = false)
    private Instant created;

    @Column(name = "game_status", nullable = false)
    private Byte gameStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameSetup getGameSetup() {
        return gameSetup;
    }

    public void setGameSetup(GameSetup gameSetup) {
        this.gameSetup = gameSetup;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Byte getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Byte gameStatus) {
        this.gameStatus = gameStatus;
    }

}