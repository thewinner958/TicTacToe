package github.thewinner958.tictactoe.game.services.mappers;

import github.thewinner958.tictactoe.data.entities.GameSetup;
import github.thewinner958.tictactoe.web.DTOs.GameSetupDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameSetupMapper {
    GameSetup toEntity(GameSetupDto gameSetupDto);

    GameSetupDto toDto(GameSetup gameSetup);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GameSetup partialUpdate(GameSetupDto gameSetupDto, @MappingTarget GameSetup gameSetup);
}