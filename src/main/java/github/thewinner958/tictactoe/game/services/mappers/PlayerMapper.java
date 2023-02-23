package github.thewinner958.tictactoe.game.services.mappers;

import github.thewinner958.tictactoe.data.entities.Player;
import github.thewinner958.tictactoe.game.services.PlayerService;
import github.thewinner958.tictactoe.game.services.DTOs.PlayerDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = PlayerService.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PlayerMapper {

    @Mappings({@Mapping(target = "isBot"),
            @Mapping(target = "id"),
            @Mapping(target = "createTime"),
            @Mapping(target = "password")})
    Player toEntity(PlayerDto playerDto);

    PlayerDto toDto(Player player);

    @Mappings({@Mapping(target = "isBot"),
            @Mapping(target = "id"),
            @Mapping(target = "createTime"),
            @Mapping(target = "password")})
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Player partialUpdate(PlayerDto playerDto, @MappingTarget Player player);
}