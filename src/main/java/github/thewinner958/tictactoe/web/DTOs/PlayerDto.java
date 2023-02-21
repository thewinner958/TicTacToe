package github.thewinner958.tictactoe.web.DTOs;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Player} entity
 */
public record PlayerDto(int id, String username, String email, String password, Instant createTime, byte isBot) implements Serializable {

}