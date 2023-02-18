package github.thewinner958.tictactoe.data.entities;

import javax.persistence.*;

@Entity(name = "GameSetup")
@Table(name = "game_setup", schema = "tictactoe")
public class GameSetup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "player1_char", nullable = false)
    private Character player1Char;

    @Column(name = "player2_char", nullable = false)
    private Character player2Char;

    @Column(name = "empty_char", nullable = false)
    private Character emptyChar;

    @Column(name = "board_rows", nullable = false)
    private Long boardRows;

    @Column(name = "board_columns", nullable = false)
    private Long boardColumns;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getPlayer1Char() {
        return player1Char;
    }

    public void setPlayer1Char(Character player1Char) {
        this.player1Char = player1Char;
    }

    public Character getPlayer2Char() {
        return player2Char;
    }

    public void setPlayer2Char(Character player2Char) {
        this.player2Char = player2Char;
    }

    public Character getEmptyChar() {
        return emptyChar;
    }

    public void setEmptyChar(Character emptyChar) {
        this.emptyChar = emptyChar;
    }

    public Long getBoardRows() {
        return boardRows;
    }

    public void setBoardRows(Long boardRows) {
        this.boardRows = boardRows;
    }

    public Long getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(Long boardColumns) {
        this.boardColumns = boardColumns;
    }

}