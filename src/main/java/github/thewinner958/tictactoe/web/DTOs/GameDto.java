package github.thewinner958.tictactoe.web.DTOs;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Game} entity
 */
public record GameDto(Integer id, PlayerDto player1, PlayerDto player2, GameSetupDto gameSetup, Instant created,
                      Byte gameStatus) implements Serializable {
}