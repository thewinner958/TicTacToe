package github.thewinner958.tictactoe.web.DTOs;

import java.io.Serializable;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.GameSetup} entity
 */
public record GameSetupDto(Integer id, Character player1Char, Character player2Char, Character emptyChar,
                           Long boardRows, Long boardColumns) implements Serializable {
}