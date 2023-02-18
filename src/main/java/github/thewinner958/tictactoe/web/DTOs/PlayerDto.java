package github.thewinner958.tictactoe.web.DTOs;

import java.io.Serializable;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Player} entity
 */
public record PlayerDto(String username, String email, String password) implements Serializable {
}