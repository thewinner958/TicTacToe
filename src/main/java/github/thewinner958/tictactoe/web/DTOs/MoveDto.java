package github.thewinner958.tictactoe.web.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Move} entity
 */
public record MoveDto(Integer id, GameDto game, PlayerDto player, Long boardRow, Long boardColumn,
                      @JsonProperty(access = JsonProperty.Access.READ_ONLY) Instant created) implements Serializable {
}