package github.thewinner958.tictactoe.web.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link github.thewinner958.tictactoe.data.entities.Player} entity
 */
public record PlayerDto(Integer id, String username, String email, @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) String password, Instant createTime, @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) Byte isBot) implements Serializable {

}