package com.crisda24.neoris.transactionaccount.domain.common.mappers.movement;
import com.crisda24.neoris.transactionaccount.domain.common.dtos.movement.MovementRequestDto;
import com.crisda24.neoris.transactionaccount.domain.enums.MovementType;
import com.crisda24.neoris.transactionaccount.domain.models.Movement;
import com.crisda24.neoris.transactionaccount.infrastructure.output.adapter.repositories.entity.MovementEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovementMapper {
    MovementMapper INSTANCE = Mappers.getMapper(MovementMapper.class);

    @Mapping(target = "idMovement", expression = "java(dto.getIdMovement() != null? dto.getIdMovement():null)")
    @Mapping(target = "movement", source ="value")
    @Mapping(target = "movementType", source = "movementType")
    @Mapping(target = "dateMovement", expression = "java(getCurrentTimestamp())")
    Movement dtoToEntity(MovementRequestDto dto);

    default Timestamp getCurrentTimestamp() {
        return new Timestamp(new java.util.Date().getTime());
    }

    default MovementType map(String value) {
        return MovementType.valueOf(value);
    }

    MovementEntity toEntity(Movement movement);
}
