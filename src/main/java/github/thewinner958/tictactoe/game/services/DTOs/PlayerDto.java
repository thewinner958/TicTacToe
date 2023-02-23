package github.thewinner958.tictactoe.game.services.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Player} entity
 */
public record PlayerDto(Integer id, String username, String email, @JsonIgnore String password, Instant createTime, @JsonIgnore Byte isBot) implements Serializable {

}