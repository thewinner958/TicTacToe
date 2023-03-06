package github.thewinner958.tictactoe.web.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Game} entity
 */
public record GameDto(Integer id, PlayerDto player1, PlayerDto player2, GameSetupDto gameSetup, @JsonProperty(access = JsonProperty.Access.READ_ONLY) Instant created,
                      @JsonProperty(access = JsonProperty.Access.READ_ONLY) Boolean gameStatus, @JsonIgnore String state, @JsonProperty(access = JsonProperty.Access.READ_ONLY) Boolean whoWon) implements Serializable {
}