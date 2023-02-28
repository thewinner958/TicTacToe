package github.thewinner958.tictactoe.web.DTOs;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Move} entity
 */
public record MoveDto(Integer id, GameDto game, PlayerDto player, Long boardRow, Long boardColumn,
                      Instant created) implements Serializable {
}