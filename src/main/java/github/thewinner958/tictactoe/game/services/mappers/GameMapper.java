package github.thewinner958.tictactoe.game.services.mappers;

import github.thewinner958.tictactoe.data.entities.Game;
import github.thewinner958.tictactoe.web.DTOs.GameDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {PlayerMapper.class, PlayerMapper.class, GameSetupMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface GameMapper {
    Game toEntity(GameDto gameDto);

    GameDto toDto(Game game);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Game partialUpdate(GameDto gameDto, @MappingTarget Game game);
}