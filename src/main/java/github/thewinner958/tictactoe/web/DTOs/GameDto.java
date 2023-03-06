package github.thewinner958.tictactoe.web.DTOs;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Game} entity
 */
public record GameDto(Integer id, PlayerDto player1, PlayerDto player2, GameSetupDto gameSetup, Instant created,
                      Boolean gameStatus, @JsonIgnore String state, Boolean whoWon) implements Serializable {
}