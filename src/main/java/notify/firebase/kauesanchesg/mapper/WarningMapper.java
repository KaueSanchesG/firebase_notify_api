package notify.firebase.kauesanchesg.mapper;

import notify.firebase.kauesanchesg.domain.entity.WarningsEntity;
import notify.firebase.kauesanchesg.dto.request.WarningRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarningMapper {

    @Mapping(target = "neighborhood", ignore = true)
    WarningsEntity toEntity(WarningRequest request);
}
