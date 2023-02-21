package github.thewinner958.tictactoe.game.services.mappers;

import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.game.services.PlayerService;
import github.thewinner958.tictactoe.web.DTOs.PlayerDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Mapper(componentModel = "spring", uses = PlayerService.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PlayerMapper {

    @Mapping(target = "isBot")
    @Mapping(target = "id")
    @Mapping(target = "createTime")
    Player toEntity(PlayerDto playerDto);

    PlayerDto toDto(Player player);

    @Mapping(target = "isBot")
    @Mapping(target = "id")
    @Mapping(target = "createTime")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDto playerDto, @MappingTarget Player player);
}