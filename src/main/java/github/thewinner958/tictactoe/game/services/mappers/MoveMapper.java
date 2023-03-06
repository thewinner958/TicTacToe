package github.thewinner958.tictactoe.game.services.mappers;

import github.thewinner958.tictactoe.data.entities.Move;
import github.thewinner958.tictactoe.web.DTOs.MoveDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component("moveMapper")
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {GameMapper.class, PlayerMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MoveMapper {
    Move toEntity(MoveDto moveDto);

    MoveDto toDto(Move move);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Move partialUpdate(MoveDto moveDto, @MappingTarget Move move);
}