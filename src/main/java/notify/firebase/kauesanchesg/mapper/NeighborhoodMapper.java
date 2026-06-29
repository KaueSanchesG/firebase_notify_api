package notify.firebase.kauesanchesg.mapper;

import notify.firebase.kauesanchesg.domain.entity.NeighborhoodEntity;
import notify.firebase.kauesanchesg.dto.response.NeighborhoodResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NeighborhoodMapper {

    NeighborhoodResponse toDTO(NeighborhoodEntity entity);

    List<NeighborhoodResponse> toDTOList(List<NeighborhoodEntity> list);
}
