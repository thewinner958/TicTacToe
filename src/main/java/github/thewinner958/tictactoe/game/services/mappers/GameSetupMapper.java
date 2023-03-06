package github.thewinner958.tictactoe.game.services.mappers;

import github.thewinner958.tictactoe.data.entities.GameSetup;
import github.thewinner958.tictactoe.game.services.GameSetupService;
import github.thewinner958.tictactoe.web.DTOs.GameSetupDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component("gameSetupMapper")
@Mapper(componentModel = "spring", uses = GameSetupService.class, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface GameSetupMapper {
    GameSetup toEntity(GameSetupDto gameSetupDto);

    GameSetupDto toDto(GameSetup gameSetup);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GameSetup partialUpdate(GameSetupDto gameSetupDto, @MappingTarget GameSetup gameSetup);
}